package net.simplifiedcoding.firebaseauthtutorial.ui.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import net.simplifiedcoding.firebaseauthtutorial.R;

public class RoomHistoryDirections {
  private RoomHistoryDirections() {
  }

  @NonNull
  public static NavDirections actionRoomHistoryToChatRoomFragment() {
    return new ActionOnlyNavDirections(R.id.action_roomHistory_to_chatRoomFragment);
  }
}
