package net.simplifiedcoding.firebaseauthtutorial;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageReceivedBindingImpl;
import net.simplifiedcoding.firebaseauthtutorial.databinding.ItemMessageSentBindingImpl;
import net.simplifiedcoding.firebaseauthtutorial.databinding.RoomListBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ITEMMESSAGERECEIVED = 1;

  private static final int LAYOUT_ITEMMESSAGESENT = 2;

  private static final int LAYOUT_ROOMLIST = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(net.simplifiedcoding.firebaseauthtutorial.R.layout.item_message_received, LAYOUT_ITEMMESSAGERECEIVED);
    INTERNAL_LAYOUT_ID_LOOKUP.put(net.simplifiedcoding.firebaseauthtutorial.R.layout.item_message_sent, LAYOUT_ITEMMESSAGESENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(net.simplifiedcoding.firebaseauthtutorial.R.layout.room_list, LAYOUT_ROOMLIST);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ITEMMESSAGERECEIVED: {
          if ("layout/item_message_received_0".equals(tag)) {
            return new ItemMessageReceivedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_message_received is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMMESSAGESENT: {
          if ("layout/item_message_sent_0".equals(tag)) {
            return new ItemMessageSentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_message_sent is invalid. Received: " + tag);
        }
        case  LAYOUT_ROOMLIST: {
          if ("layout/room_list_0".equals(tag)) {
            return new RoomListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for room_list is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/item_message_received_0", net.simplifiedcoding.firebaseauthtutorial.R.layout.item_message_received);
      sKeys.put("layout/item_message_sent_0", net.simplifiedcoding.firebaseauthtutorial.R.layout.item_message_sent);
      sKeys.put("layout/room_list_0", net.simplifiedcoding.firebaseauthtutorial.R.layout.room_list);
    }
  }
}
