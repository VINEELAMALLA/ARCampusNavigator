package com.example.arnavigationapp.admin.all_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arnavigationapp.R
import com.example.arnavigationapp.databinding.FragmentInitializeLocationsBinding

/**
 * Fragment for initializing the database with college location data
 */
class InitializeLocationsFragment : Fragment() {

    private var _binding: FragmentInitializeLocationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitializeLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInitializeButton()
    }

    private fun setupInitializeButton() {
        binding.initializeButton.setOnClickListener {
            // Show progress
            binding.progressBar.visibility = View.VISIBLE
            binding.initializeButton.isEnabled = false
            
            // Get selected college
            val collegeName = when {
                binding.radioMvgr.isChecked -> "MVGR College"
                binding.radioGvpce.isChecked -> "GVPCE College"
                else -> null // All colleges
            }
            
            // Get prefix option
            val usePrefix = binding.prefixCheckbox.isChecked
            
            // Initialize database
            viewModel.initializeCollegeLocations(collegeName, usePrefix) { success ->
                // Update UI on main thread
                activity?.runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.statusText.visibility = View.VISIBLE
                    
                    if (success) {
                        binding.statusText.text = "Initialization completed successfully!"
                        Toast.makeText(context, "Database initialized successfully", Toast.LENGTH_SHORT).show()
                        
                        // Navigate back after a short delay
                        binding.root.postDelayed({
                            findNavController().popBackStack()
                        }, 1500)
                    } else {
                        binding.statusText.text = "Initialization failed. Please try again."
                        binding.initializeButton.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}