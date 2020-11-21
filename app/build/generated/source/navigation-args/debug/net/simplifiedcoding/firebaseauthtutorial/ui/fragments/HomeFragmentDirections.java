package net.simplifiedcoding.firebaseauthtutorial.ui.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import net.simplifiedcoding.firebaseauthtutorial.R;

public class HomeFragmentDirections {
  private HomeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionHomeFragmentToChatRoomFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_chatRoomFragment);
  }

  @NonNull
  public static NavDirections actionHomeFragmentToRoomHistory() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_roomHistory);
  }

  @NonNull
  public static NavDirections actionHomeFragmentToSearchWaiting() {
    return new ActionOnlyNavDirections(R.id.action_homeFragment_to_searchWaiting);
  }
}
