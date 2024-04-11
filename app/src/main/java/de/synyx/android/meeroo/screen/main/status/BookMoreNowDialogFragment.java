package de.synyx.android.meeroo.screen.main.status;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.joda.time.Duration;

import de.synyx.android.meeroo.R;


/**
 * @author Max Dobler - dobler@synyx.de
 */
public class BookMoreNowDialogFragment extends DialogFragment {

    BookMoreNowDialogListener bookMoreNowDialogListener;

    private Duration duration;
    Long[] bookMoreDuration = {120L, 180L, 240L, 480L};

    public BookMoreNowDialogFragment addDuration(Duration duration) {

        this.duration = duration;

        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String[] bookMoreChoices = {
                getString(R.string.book_more_now_suffix_hours, "2"),
                getString(R.string.book_more_now_suffix_hours, "3"),
                getString(R.string.book_more_now_suffix_hours, "4"),
                getString(R.string.book_more_now_suffix_hours, "8"),
        };

        return new AlertDialog.Builder(getActivity()) //
                .setTitle(R.string.book_now_dialog_title)
                .setSingleChoiceItems(bookMoreChoices, 0, (dialog, which) -> {
                    this.duration = Duration.standardMinutes(bookMoreDuration[which]);
                })
                .setPositiveButton(R.string.book_now_dialog_confirm,
                        (dialog, which) -> bookMoreNowDialogListener.bookNow(duration))
                .setNegativeButton(R.string.book_now_dialog_cancel, (dialog, which) -> {
                }).create();
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            bookMoreNowDialogListener = (BookMoreNowDialogListener) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(getActivity().getClass().getSimpleName()
                    + " must implement BookNowDialogListener");
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {

        super.onDismiss(dialog);
        bookMoreNowDialogListener.onBookNowDialogDismiss();
    }

    public interface BookMoreNowDialogListener {

        void bookNow(Duration duration);


        void onBookNowDialogDismiss();
    }
}
