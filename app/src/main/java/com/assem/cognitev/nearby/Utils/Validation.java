package com.assem.cognitev.nearby.Utils;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.Objects;

public class Validation {

    public boolean isEditTextEmpty(TextInputEditText textInputEditText) {
        return Objects.requireNonNull(textInputEditText.getText()).toString().isEmpty();
    }


    public boolean validateName(String name) {
        return name.length() >= 1;
    }

    public boolean validateEmail(String email) {
        return email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public boolean validatePhone(String phone) {
        return phone.matches("^[+\\d{3}]?\\d{11,20}$");
    }

    public boolean validatePassword(String password) {
        return password.length() >= 6;
    }

    public boolean validateCheckBox(CheckBox checkBox) {
        return checkBox.isChecked();
    }

    public boolean validateRadioGroup(RadioGroup radioGroup) {
        return radioGroup.getCheckedRadioButtonId() != -1;
    }

    public boolean validate(boolean validationFlag, TextInputLayout textInputLayout, TextInputEditText textInputEditText, String emptyFieldErrorMsg, String invalidFormErrorMsg) {
        String input = Objects.requireNonNull(textInputEditText.getText()).toString();
        if (!input.isEmpty())
            if (validationFlag) {
                disableError(textInputLayout);
                return true;
            } else {
                enableError(textInputLayout, textInputEditText, invalidFormErrorMsg);
                return false;
            }
        else {
            enableError(textInputLayout, textInputEditText, emptyFieldErrorMsg);
            return false;
        }
    }


    public void enableError(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String errorMsg) {
        textInputLayout.setErrorEnabled(true);
        textInputEditText.setError(errorMsg);
    }

    public void disableError(TextInputLayout textInputLayout) {
        textInputLayout.setErrorEnabled(false);
    }

    public String getInput(TextInputEditText textInputEditText) {
        return Objects.requireNonNull(textInputEditText.getText()).toString();
    }
}

