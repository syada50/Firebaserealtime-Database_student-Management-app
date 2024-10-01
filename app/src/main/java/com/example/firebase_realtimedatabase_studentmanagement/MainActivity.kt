package com.example.firebase_realtimedatabase_studentmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase_realtimedatabase_studentmanagement.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()
    private lateinit var dataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataAdapter = DataAdapter(
            emptyList(),
            { data -> OnEditItemClick(data) },  // Edit listener
            { data -> OnDeleteItemClick(data) } // Delete listener
        )



        binding.recycleView.adapter = dataAdapter
        binding.recycleView.layoutManager = LinearLayoutManager(this)

        dataViewModel.dataList.observe(this, Observer {
            if (it != null) {
                dataAdapter.updateData(it)
            } else {
                Toast.makeText(this@MainActivity, "Error Fetching Data", Toast.LENGTH_SHORT).show()
            }
        })

        dataViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.saveBtn.setOnClickListener {
            val studid = binding.idEtxt.text.toString()
            val name = binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectEtxt.text.toString()
            val birthDate = binding.birthDateEtxt.text.toString()

            if (studid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthDate.isNotEmpty()) {
                val data = Data(null, studid, name, email, subject, birthDate)
                dataViewModel.addData(data,
                    onSuccess = {
                        clearInputField()
                        Toast.makeText(
                            this@MainActivity,
                            "DATA SAVED SUCCESSFULLY",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onFailure = {
                        Toast.makeText(this@MainActivity, "FAILED TO ADD DATA", Toast.LENGTH_SHORT)
                            .show()
                    })
            }
        }
    }

    private fun clearInputField() {
        binding.idEtxt.text?.clear()
        binding.nameEtxt.text?.clear()
        binding.emailEtxt.text?.clear()
        binding.subjectEtxt.text?.clear()
        binding.birthDateEtxt.text?.clear()
    }

    fun OnEditItemClick(data: Data) {
        binding.idEtxt.setText(data.studid)
        binding.nameEtxt.setText(data.name)
        binding.emailEtxt.setText(data.email)
        binding.subjectEtxt.setText(data.subject)
        binding.birthDateEtxt.setText(data.birthDate)

        binding.saveBtn.setOnClickListener {
            val updateData = Data(
                data.id,
                binding.idEtxt.text.toString(),
                binding.nameEtxt.text.toString(),
                binding.emailEtxt.text.toString(),
                binding.subjectEtxt.text.toString(),
                binding.birthDateEtxt.text.toString()
            )

            dataViewModel.updateDAta(updateData)
            clearInputField()
            Toast.makeText(this@MainActivity, "Data updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun OnDeleteItemClick(data: Data) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Confirmation")
            setMessage("Are you sure?")
            setPositiveButton("Yes") { _, _ ->
                dataViewModel.deleteDAta(data,
                    onSuccess = {
                        Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to delete data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
            setNegativeButton("No", null)
        }.show()
    }
}
