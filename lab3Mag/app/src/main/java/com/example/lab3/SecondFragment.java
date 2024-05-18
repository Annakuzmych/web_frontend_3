package com.example.lab3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lab3.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private static final String TAG = "SecondFragment";
    private List<String> selectedOptions = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        binding.buttonSubmit.setOnClickListener(v -> submitOptions());

        fetchQuestions();
    }

    private void fetchQuestions() {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Model>> call = methods.getAllData();

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Model> questions = response.body();

                    for (Model question : questions) {
                        TextView textView = new TextView(getContext());
                        textView.setText(question.getQuestion());
                        binding.optionsLayout.addView(textView);

                        List<String> options = question.getOptionsList();
                        for (String option : options) {
                            CheckBox checkBox = new CheckBox(getContext());
                            checkBox.setText(option);
                            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if (isChecked) {
                                    selectedOptions.add(option);
                                } else {
                                    selectedOptions.remove(option);
                                }
                            });
                            binding.optionsLayout.addView(checkBox);
                        }
                    }
                } else {
                    Log.e(TAG, "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage());
            }
        });
    }

    private void submitOptions() {
        StringBuilder selectedOptionsText = new StringBuilder();
        for (String option : selectedOptions) {
            selectedOptionsText.append(option).append(", ");
        }
        if (selectedOptionsText.length() > 0) {
            selectedOptionsText.deleteCharAt(selectedOptionsText.length() - 2); // Remove the last comma and space
        }
        // Do something with the selected options, e.g., send them to a server
        Log.d(TAG, "Selected options: " + selectedOptionsText.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
