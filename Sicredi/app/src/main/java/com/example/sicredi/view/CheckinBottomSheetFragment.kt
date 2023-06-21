package com.example.sicredi.view

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.sicredi.databinding.FragmentCheckinBottomSheetBinding
import com.example.sicredi.di.Injection
import com.example.sicredi.viewmodel.CheckinViewModel
import com.example.sicredi.model.Event
import androidx.fragment.app.viewModels
import com.example.sicredi.model.Checkin

class CheckinBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckinBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var event: Event
    private val viewModel by viewModels<CheckinViewModel> {
        Injection.provideViewModelFactory()
    }
    companion object {
        fun newInstance(event: Event) = CheckinBottomSheetFragment().apply {
            arguments = bundleOf("event" to event)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckinBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event = requireArguments().getSerializable("event") as Event
        setBottomSheetTitleAndDate()

        binding.bottomSheetCheckinButton.setOnClickListener {
            setCheckInButtonAction(it)
        }
    }

    private fun setCheckInButtonAction(it: View) {
        binding.checkinButtonText.visibility = View.INVISIBLE
        binding.checkinButtonAnimation.visibility = View.VISIBLE
        validateFildsAndPostCheckIn(it)
    }

    private fun setBottomSheetTitleAndDate() {
        binding.bottomSheetTitle.text = event.title
        binding.bottomSheetDate.text = event.date.toString()
    }

    private fun validateFildsAndPostCheckIn(it: View) {
        if(binding.nameInput.text.isNullOrBlank() ||
            binding.emailInput.text.isNullOrBlank() ||
            !Patterns.EMAIL_ADDRESS
                .matcher(binding.emailInput.text.toString()).matches()) {

            validateTextFields()

        } else {
            it.isClickable = false
            viewModel.makeCheckin(Checkin(
                event.id,
                binding.nameInput.text.toString(),
                binding.emailInput.text.toString()
            ))
        }
    }

    private fun validateTextFields() {
        binding.checkinButtonText.visibility = View.VISIBLE
        binding.checkinButtonAnimation.visibility = View.INVISIBLE

        if (binding.nameInput.text.isNullOrBlank()) {
            binding.nameInput.error = "Campo obrigatório"
        }

        if (binding.emailInput.text.isNullOrBlank()) {
            binding.emailInput.error = "Campo obrigatório"
        }

        else if (!Patterns.EMAIL_ADDRESS
                .matcher(binding.emailInput.text.toString()).matches()
        ) {
            binding.emailInput.error = "Insira um email válido"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}