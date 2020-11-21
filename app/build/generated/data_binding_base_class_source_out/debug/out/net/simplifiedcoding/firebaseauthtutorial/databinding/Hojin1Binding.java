// Generated by view binder compiler. Do not edit!
package net.simplifiedcoding.firebaseauthtutorial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import net.simplifiedcoding.firebaseauthtutorial.R;

public final class Hojin1Binding implements ViewBinding {
  @NonNull
  private final TableLayout rootView;

  @NonNull
  public final TextView KDA;

  @NonNull
  public final TextView KDAaverage;

  @NonNull
  public final TextView cs;

  @NonNull
  public final TextView gold;

  @NonNull
  public final TextView matchaverage;

  @NonNull
  public final TableRow tableKDA;

  @NonNull
  public final TableRow tableaverage;

  @NonNull
  public final TableRow tablecs;

  @NonNull
  public final TableRow tablegold;

  @NonNull
  public final TableRow tablematch;

  @NonNull
  public final TableRow tabletime;

  @NonNull
  public final TableRow tabletype;

  @NonNull
  public final TableRow tablewin;

  @NonNull
  public final TextView timeaverage;

  @NonNull
  public final TextView type;

  @NonNull
  public final TextView win;

  private Hojin1Binding(@NonNull TableLayout rootView, @NonNull TextView KDA,
      @NonNull TextView KDAaverage, @NonNull TextView cs, @NonNull TextView gold,
      @NonNull TextView matchaverage, @NonNull TableRow tableKDA, @NonNull TableRow tableaverage,
      @NonNull TableRow tablecs, @NonNull TableRow tablegold, @NonNull TableRow tablematch,
      @NonNull TableRow tabletime, @NonNull TableRow tabletype, @NonNull TableRow tablewin,
      @NonNull TextView timeaverage, @NonNull TextView type, @NonNull TextView win) {
    this.rootView = rootView;
    this.KDA = KDA;
    this.KDAaverage = KDAaverage;
    this.cs = cs;
    this.gold = gold;
    this.matchaverage = matchaverage;
    this.tableKDA = tableKDA;
    this.tableaverage = tableaverage;
    this.tablecs = tablecs;
    this.tablegold = tablegold;
    this.tablematch = tablematch;
    this.tabletime = tabletime;
    this.tabletype = tabletype;
    this.tablewin = tablewin;
    this.timeaverage = timeaverage;
    this.type = type;
    this.win = win;
  }

  @Override
  @NonNull
  public TableLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static Hojin1Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static Hojin1Binding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.hojin_1, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static Hojin1Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      TextView KDA = rootView.findViewById(R.id.KDA);
      if (KDA == null) {
        missingId = "KDA";
        break missingId;
      }
      TextView KDAaverage = rootView.findViewById(R.id.KDAaverage);
      if (KDAaverage == null) {
        missingId = "KDAaverage";
        break missingId;
      }
      TextView cs = rootView.findViewById(R.id.cs);
      if (cs == null) {
        missingId = "cs";
        break missingId;
      }
      TextView gold = rootView.findViewById(R.id.gold);
      if (gold == null) {
        missingId = "gold";
        break missingId;
      }
      TextView matchaverage = rootView.findViewById(R.id.matchaverage);
      if (matchaverage == null) {
        missingId = "matchaverage";
        break missingId;
      }
      TableRow tableKDA = rootView.findViewById(R.id.tableKDA);
      if (tableKDA == null) {
        missingId = "tableKDA";
        break missingId;
      }
      TableRow tableaverage = rootView.findViewById(R.id.tableaverage);
      if (tableaverage == null) {
        missingId = "tableaverage";
        break missingId;
      }
      TableRow tablecs = rootView.findViewById(R.id.tablecs);
      if (tablecs == null) {
        missingId = "tablecs";
        break missingId;
      }
      TableRow tablegold = rootView.findViewById(R.id.tablegold);
      if (tablegold == null) {
        missingId = "tablegold";
        break missingId;
      }
      TableRow tablematch = rootView.findViewById(R.id.tablematch);
      if (tablematch == null) {
        missingId = "tablematch";
        break missingId;
      }
      TableRow tabletime = rootView.findViewById(R.id.tabletime);
      if (tabletime == null) {
        missingId = "tabletime";
        break missingId;
      }
      TableRow tabletype = rootView.findViewById(R.id.tabletype);
      if (tabletype == null) {
        missingId = "tabletype";
        break missingId;
      }
      TableRow tablewin = rootView.findViewById(R.id.tablewin);
      if (tablewin == null) {
        missingId = "tablewin";
        break missingId;
      }
      TextView timeaverage = rootView.findViewById(R.id.timeaverage);
      if (timeaverage == null) {
        missingId = "timeaverage";
        break missingId;
      }
      TextView type = rootView.findViewById(R.id.type);
      if (type == null) {
        missingId = "type";
        break missingId;
      }
      TextView win = rootView.findViewById(R.id.win);
      if (win == null) {
        missingId = "win";
        break missingId;
      }
      return new Hojin1Binding((TableLayout) rootView, KDA, KDAaverage, cs, gold, matchaverage,
          tableKDA, tableaverage, tablecs, tablegold, tablematch, tabletime, tabletype, tablewin,
          timeaverage, type, win);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}