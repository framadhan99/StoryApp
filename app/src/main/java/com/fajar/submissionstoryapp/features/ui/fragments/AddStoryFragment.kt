package com.fajar.submissionstoryapp.features.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.fajar.submissionstoryapp.features.utils.*
import com.fajar.submissionstoryapp.features.utils.Const.CAMERA_X_RESULT
import com.fajar.submissionstoryapp.features.utils.Const.PERMISSIONS
import com.fajar.submissionstoryapp.R
import com.fajar.submissionstoryapp.databinding.FragmentAddStoryBinding
import com.fajar.submissionstoryapp.features.ui.activity.CameraActivity
import com.fajar.submissionstoryapp.features.ui.viewmodel.AddStoryViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class AddStoryFragment : Fragment() {

    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding!!

    private var getFile: File? = null
    private var result: Bitmap? = null
    private val addStoryVM: AddStoryViewModel by viewModels()

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                showToast(requireContext(), getString(R.string.error_permission))
            }
        }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            result =
                rotateBitmap(
                    BitmapFactory.decodeFile(getFile?.path),
                    isBackCamera
                )
        }
        binding.ivPreview.setImageBitmap(result)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            getFile = myFile
            binding.ivPreview.setImageURI(selectedImg)
        }
    }

    private fun getPermissions() {
        activity?.let {
            if (hasPermissions(activity as Context, PERMISSIONS)) {
                showToast(requireContext(), "Has Permissions")
            } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity?)?.hideLoading()

        getPermissions()

        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnCamera.setOnClickListener {
            startCamera()
        }
        binding.btnUpload.setOnClickListener {
            uploadImage()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    private fun startCamera() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCamera.launch(intent)
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun uploadImage() {

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val description = binding.textAddstoryLayout.editText?.text.toString()
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            validateData(description, imageMultipart)

        } else {
            showToast(requireContext(), "Data Null")
        }
    }

    private fun validateData(desc: String, img: MultipartBody.Part) {
        addStoryVM.tokenUser.observe(viewLifecycleOwner) {
            addStoryVM.addStory(it, desc, img).observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    when (data.status) {
                        Status.LOADING -> {
                            (activity as HomeActivity?)?.showLoading()
                        }
                        Status.SUCCESS -> {
                            (activity as HomeActivity?)?.hideLoading()
                            requireActivity().finish()
                            val intent = Intent(requireActivity(), HomeActivity::class.java)
                            requireActivity().startActivity(intent)
                            showToast(requireContext(), "Upload Successful")
                        }
                        Status.ERROR -> {
                            (activity as HomeActivity?)?.hideLoading()
                            Log.d(this.toString(), data.message.toString())
                        }
                    }
                } else showToast(requireContext(), getString(R.string.error_data_null))
            }
        }
    }

}