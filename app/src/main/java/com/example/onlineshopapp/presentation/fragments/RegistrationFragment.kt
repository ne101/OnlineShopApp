package com.example.onlineshopapp.presentation.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlineshopapp.R
import com.example.onlineshopapp.databinding.FragmentRegistrationBinding
import com.example.onlineshopapp.domain.entities.PersonEntity
import com.example.onlineshopapp.presentation.app.OnlineShopApplication
import com.example.onlineshopapp.presentation.viewModels.RegistrationViewModel
import com.example.onlineshopapp.presentation.viewModels.ViewModelFactory
import com.redmadrobot.inputmask.MaskedTextChangedListener
import javax.inject.Inject


class RegistrationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RegistrationViewModel

    private val component by lazy {
        (requireActivity().application as OnlineShopApplication).component
    }
    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]
        addCustomToolBar()
        nameValid()
        secondNameValid()
        numberValid()
        observe()
        launchNextScreen()
        registered()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addCustomToolBar() {
        binding.customToolbar.tvScreenName.text = "Вход"
    }

    private fun nameValid() {
        binding.TIName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.checkNameValid(binding.TIName)
                if (binding.TIName.text.toString().isNotEmpty()) {
                    binding.ivCrossName.visibility = View.VISIBLE
                } else {
                    binding.ivCrossName.visibility = View.INVISIBLE
                }
                binding.ivCrossName.setOnClickListener {
                    if (binding.ivCrossName.visibility == View.VISIBLE) {
                        binding.TIName.setText("")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun secondNameValid() {
        binding.TISecondName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.checkSecondNameValid(binding.TISecondName)
                if (binding.TISecondName.text.toString().isNotEmpty()) {
                    binding.ivCrossSecondName.visibility = View.VISIBLE
                } else {
                    binding.ivCrossSecondName.visibility = View.INVISIBLE
                }
                binding.ivCrossSecondName.setOnClickListener {
                    if (binding.ivCrossSecondName.visibility == View.VISIBLE) {
                        binding.TISecondName.setText("")
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun numberValid() {
        val str = "+7 ([000]) [000]-[00]-[00]"
        val maxDigits = str.length - 8

        val listener =
            MaskedTextChangedListener(str, true, binding.TINumber, null,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {
                        if (extractedValue.filter { it.isDigit() }.length >= maxDigits) {
                            // Если количество введенных цифр достигло максимума, прекратите ввод
                            binding.TINumber.setText(extractedValue.substring(0, maxDigits))
                        } else {
                            viewModel.checkNumberValid(binding.TINumber)
                            if (binding.TINumber.text.toString().isNotEmpty()) {
                                binding.ivCrossPhone.visibility = View.VISIBLE
                            } else {
                                binding.ivCrossPhone.visibility = View.INVISIBLE
                            }
                            binding.ivCrossPhone.setOnClickListener {
                                if (binding.ivCrossPhone.visibility == View.VISIBLE) {
                                    binding.TINumber.setText("")
                                }
                            }
                        }
                    }
                })
        binding.TINumber.addTextChangedListener(listener)
        binding.TINumber.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.TINumber.text.toString().isEmpty()) {
                binding.TINumber.setText("+7 ")
            }
            listener.onFocusChange(binding.TINumber, hasFocus)
        }
    }

    private fun observe() {
        viewModel.allValid.observe(viewLifecycleOwner) {
            if (it) {
                binding.button.isEnabled = true
                binding.button.setBackgroundColor(Color.parseColor("#D62F89"))
            } else {
                binding.button.isEnabled = false
                binding.button.setBackgroundColor(Color.parseColor("#FF8AC9"))
            }
        }
    }

    private fun registered(){
        viewModel.person.observe(viewLifecycleOwner) {
            if (it != null ) {
                findNavController().navigate(R.id.action_registrationFragment_to_navigationFragment)
            }
        }
    }

    private fun launchNextScreen() {
        binding.button.setOnClickListener {
            val person = PersonEntity(
                0,
                viewModel.name.value.toString(),
                viewModel.secondName.value.toString(),
                viewModel.phone.value.toString()
            )
            viewModel.addPerson(person)
            findNavController().navigate(R.id.action_registrationFragment_to_navigationFragment)
        }
    }
}