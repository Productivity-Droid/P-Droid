package com.techov8.p_droid.AUTO_REPLY.model.data;

public class ContactHolder {
    private final String contactName;
    private boolean isChecked;

    public ContactHolder(String contactName, boolean isChecked) {
        this.contactName = contactName;
        this.isChecked = isChecked;
    }

    public String getContactName() {
        return contactName;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
