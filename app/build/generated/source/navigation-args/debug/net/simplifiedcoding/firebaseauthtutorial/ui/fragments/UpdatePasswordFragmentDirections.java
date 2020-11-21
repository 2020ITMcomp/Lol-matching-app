package net.simplifiedcoding.firebaseauthtutorial.ui.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import net.simplifiedcoding.firebaseauthtutorial.R;

public class UpdatePasswordFragmentDirections {
  private UpdatePasswordFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPasswordUpdated() {
    return new ActionOnlyNavDirections(R.id.actionPasswordUpdated);
  }
}
