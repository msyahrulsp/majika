package com.example.majika.fragments

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapters.BranchAdapter
import com.example.majika.models.APIResponse
import com.example.majika.models.Branch
import com.example.majika.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BranchFragment : Fragment() {
    lateinit var adapter: BranchAdapter
    lateinit var branchRecyclerView: RecyclerView
    lateinit var spinner: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_branch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.progressBar)
        branchRecyclerView = view.findViewById(R.id.recyclerview_branch)
        branchRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BranchAdapter(context!!, listOf())
        branchRecyclerView.adapter = adapter
        getBranches()
    }

    private fun getBranches() {
        val retrofitClient = RetrofitClient.getBranch()
        retrofitClient.getBranch().enqueue(object: Callback<APIResponse<Branch>> {
            override fun onResponse(call: Call<APIResponse<Branch>>, response: Response<APIResponse<Branch>>) {
                if (response.isSuccessful && isAdded) {
                    val branchList = response.body()?.data
                    if (branchList != null) {
                        spinner.visibility = View.GONE
                        branchRecyclerView.adapter = BranchAdapter(context!!, branchList)
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show()
                        replaceFragment(ErrorFragment("No Data", "Maaf, tapi datanya kosong :("))
                    }
                }
            }

            override fun onFailure(call: Call<APIResponse<Branch>>, t: Throwable) {
                if (isAdded) {
                    d("Error", t.message.toString())
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    replaceFragment(ErrorFragment("Error", t.message.toString()))
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
        fragmentTransaction?.commit()
    }
}