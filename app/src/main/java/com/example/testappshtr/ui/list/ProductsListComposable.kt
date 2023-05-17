package com.example.testappshtr.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.testappshtr.R
import com.example.testappshtr.service.BackendResult

@Composable
fun ProductsListComposable(viewModel: MainViewModel) {
    val productsListState = viewModel.productsFlow.collectAsState()
    val productList = productsListState.value


    when (productList) {
        is BackendResult.Complete -> {

            Column {
                (productsListState.value as BackendResult.Complete).data.forEach {
                    val titleString = stringResource(id = R.string.price_prefix).format(
                        it.name,
                        it.newPrice.toString()
                    )
                    Text(text = titleString)
                }
            }
        }
        is BackendResult.Error -> {
            //snackbar show error
            Text(text = productList.text)
        }
        is BackendResult.Loading -> {
            //show loader
            Text(text = "Loading")
        }
        else -> Unit
    }
}