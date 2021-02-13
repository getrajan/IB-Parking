package net.ideabreed.ibparking.presenter.adapter; /*
 * Created by Rajan Karki on 2/13/21
 * Copyright @2021
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.ideabreed.ibparking.R;
import net.ideabreed.ibparking.model.ReceiptItem;

import java.util.ArrayList;

public class CheckOutReceiptListAdapter extends ArrayAdapter<ReceiptItem> {
    public CheckOutReceiptListAdapter(@NonNull Context context, ArrayList<ReceiptItem> receiptItems) {
        super(context, 0, receiptItems);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReceiptItem receiptItem = getItem(position);
        ReceiptViewHolder receiptViewHolder;
        final View view;
        if (convertView == null) {
            receiptViewHolder = new ReceiptViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.check_out_receipt_list_item, parent, false);
            receiptViewHolder.key = convertView.findViewById(R.id.checkOutReceiptItemKeyTV);
            receiptViewHolder.value = convertView.findViewById(R.id.checkOutReceiptItemValueTV);
            view = convertView;
            convertView.setTag(receiptViewHolder);
            receiptViewHolder.key.setText(receiptItem.getKey());
            receiptViewHolder.value.setText(receiptItem.getValue());

        }

        return convertView;
    }

    private static class ReceiptViewHolder {
        TextView key;
        TextView value;
    }
}