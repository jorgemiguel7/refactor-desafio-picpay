package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.picpay.desafio.android.presentation.contacts.ContactsScreen
import com.picpay.desafio.android.presentation.contacts.ContactsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = koinViewModel<ContactsViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ContactsScreen(
                uiState = uiState,
                effect = viewModel.effects
            )
        }
    }
}