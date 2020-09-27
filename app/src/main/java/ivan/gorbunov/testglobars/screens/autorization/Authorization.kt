package ivan.gorbunov.testglobars.screens.autorization

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ivan.gorbunov.testglobars.R
import ivan.gorbunov.testglobars.model.retrofit.data.UserLogin


class Authorization : Fragment() {

    private lateinit var nameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var buttonEnter: Button
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_authorization, container, false)

        nameField = root.findViewById(R.id.user_edit_text)
        passwordField = root.findViewById(R.id.password_edit_text)
        buttonEnter = root.findViewById(R.id.button_enter)
        progressBar = root.findViewById(R.id.progressBar)

        val viewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)

        buttonEnter.setOnClickListener {
            getToken(
                viewModel,
                nameField.text.toString().trim(),
                passwordField.text.toString().trim()
            )
            progressBar.visibility = View.VISIBLE
        }

        viewModel.token.observe(viewLifecycleOwner, { tokenA ->
            tokenA?.let {
                if (viewModel.isSuccess.value!!) {
                    this.findNavController().navigate(
                        AuthorizationDirections.actionAuthorizationToMapsFragment(tokenA)
                    )
                } else {
                    progressBar.visibility = View.GONE
                    buildDialog(viewModel)
                }
            }
        })

        return root
    }

    private fun getToken(viewModel: AuthorizationViewModel, name: String, password: String) {
        val user = UserLogin(name, password)
        viewModel.getToken(user)
    }

    private fun buildDialog(viewModel: AuthorizationViewModel) {
        val builder = AlertDialog.Builder(activity)
        val msg = viewModel.token.value?.replaceFirst("Bearer ", "")
        builder.setMessage("$msg\nPlease,try again")
            .setTitle("Error")
        val dialog = builder.create()
        dialog.show()
    }


}