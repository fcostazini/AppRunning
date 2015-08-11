package studios.thinkup.com.apprunning.dialogs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by FaQ on 23/05/2015.
 */
public class DatePicker extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;
    private Date minDate;
    private Date maxDate;
    private Date initDate;

    public void setInitialDate(final Date date) {
        this.initDate = date;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public DatePickerDialog.OnDateSetListener getListener() {
        return listener;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        if (this.initDate != null)
            c.setTime(this.initDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog d = new DatePickerDialog(getActivity(), this.getListener(), year, month, day);
        if (this.getMinDate() != null)
            d.getDatePicker().setMinDate(this.getMinDate().getTime());
        if (this.getMaxDate() != null)
            d.getDatePicker().setMaxDate(this.getMaxDate().getTime());
        return d;
    }

}
