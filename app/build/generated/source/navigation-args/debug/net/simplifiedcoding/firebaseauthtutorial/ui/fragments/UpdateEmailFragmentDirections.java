package net.simplifiedcoding.firebaseauthtutorial.ui.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import net.simplifiedcoding.firebaseauthtutorial.R;

public class UpdateEmailFragmentDirections {
  private UpdateEmailFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEmailUpdated() {
    return new ActionOnlyNavDirections(R.id.actionEmailUpdated);
  }
}
