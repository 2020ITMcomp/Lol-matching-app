package net.simplifiedcoding.firebaseauthtutorial.ui.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import net.simplifiedcoding.firebaseauthtutorial.R;

public class ProfileFragmentDirections {
  private ProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionUpdateEmail() {
    return new ActionOnlyNavDirections(R.id.actionUpdateEmail);
  }

  @NonNull
  public static NavDirections actionUpdatePassword() {
    return new ActionOnlyNavDirections(R.id.actionUpdatePassword);
  }
}
