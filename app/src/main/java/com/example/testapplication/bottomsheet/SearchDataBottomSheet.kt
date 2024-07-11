package com.example.testapplication.bottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.R
import com.example.testapplication.databinding.BottomsheetSearchDataBinding
import com.example.testapplication.viewmodel.MainActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class SearchDataBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetSearchDataBinding
    private lateinit var dialog: BottomSheetDialog

    private lateinit var viewModel: MainActivityViewModel

    var bottomSheetTitlelist: ArrayList<String> = arrayListOf()
    var countData:String =""

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    constructor(bottomSheetTitlelist: ArrayList<String>,countData:String) : this(){
        this.bottomSheetTitlelist = bottomSheetTitlelist
        this.countData = countData
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottomsheet_search_data, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

       // binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireContext(), theme)

        return dialog
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }



    private fun initView() {

        binding.tvData.setText("List item = "+bottomSheetTitlelist.toString()
                +"\n\n"+"List item count = "+bottomSheetTitlelist.size+" items"
                +countData)

        binding.ivClose.setOnClickListener {
            dialog.dismiss()
        }

    }


    override fun getTheme() = R.style.BottomSheetDialog

}