package com.example.onlineshoppingapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.onlineshoppingapp.R
import com.example.onlineshoppingapp.databinding.FragmentOrderDetailBinding
import com.example.onlineshoppingapp.viewmodel.ProfileViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class OrderDetailFragment : Fragment(), PaymentResultListener {
    lateinit var binding:FragmentOrderDetailBinding
    private val viewModel:ProfileViewModel by viewModels()
    var ttl:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_order_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetails()
        getAlldata()
        Checkout.preload(activity?.getApplicationContext())

        binding.paymentProceed.setOnClickListener {
            makePayment()
        }
    }

    private fun getAlldata() {
        val name=activity?.intent!!.getStringExtra("name")
        val price=activity?.intent!!.getStringExtra("price")
        val url=activity?.intent!!.getStringExtra("url")
       val totalprice= activity?.intent!!.getIntExtra("tPrice",0)
       val quantity= activity?.intent!!.getIntExtra("q",0)
        Glide.with(requireContext())
            .load(url)
            .into(binding.imageCart)
        binding.name.setText(name)
        binding.price.setText(price)
        Log.d("total",totalprice.toString())
         ttl=totalprice.toString()
        var Qty=quantity.toString()
      binding.totalprice.setText(ttl)
       binding.quantity.setText(Qty)
    }
    private fun makePayment() {
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Learning Worldz")
            options.put("description","Learning Worldz Payment")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            val price= ttl.toInt()*100
            Log.d("price",price.toString())
            options.put("amount",price.toString())//pass amount in currency subunits

            /* val retryObj =  JSONObject()
             retryObj.put("enabled", true);
             retryObj.put("max_count", 4);
             options.put("retry", retryObj);*/

            val prefill = JSONObject()
            viewModel.result.observe(viewLifecycleOwner, Observer {
                prefill.put("email",it.email)
                prefill.put("contact","8527834283")
            })


            options.put("prefill",prefill)
            co.open(requireActivity(),options)
        }catch (e: Exception){
            Toast.makeText(requireContext(),"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(requireContext(),"Payment Sucsess: "+ p0, Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(requireContext(),"Payment Failed: "+ p1, Toast.LENGTH_LONG).show()
    }
}