package com.chaitanya.lab_a1_a2_android_chaitanya_824156.adapters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeProductCallback extends ItemTouchHelper.SimpleCallback {
    private ProductAdapter mAdapter;
    private Drawable deleteIcon, editIcon;
    private final ColorDrawable backgroundRed, backgroundGreen;

    public SwipeProductCallback(ProductAdapter mAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mAdapter = mAdapter;
        deleteIcon = ContextCompat.getDrawable(mAdapter.getContext(),
                android.R.drawable.ic_delete);
        editIcon = ContextCompat.getDrawable(mAdapter.getContext(),
                android.R.drawable.ic_menu_edit);
        backgroundRed = new ColorDrawable(Color.RED);
        backgroundGreen = new ColorDrawable(Color.GREEN);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

        int editMargin = (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
        int editTop = itemView.getTop() + (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
        int editBottom = iconTop + editIcon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + deleteIcon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            backgroundRed.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
            backgroundRed.draw(c);
            deleteIcon.draw(c);

        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - editMargin - editIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - editMargin;
            editIcon.setBounds(iconLeft, editTop, iconRight, editBottom);

            backgroundGreen.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
            backgroundGreen.draw(c);
            editIcon.draw(c);
        } else { // view is unSwiped
            backgroundRed.setBounds(0, 0, 0, 0);
            backgroundGreen.setBounds(0, 0, 0, 0);
        }


    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT) {
            mAdapter.deleteAction(position);
        } else {
            mAdapter.editAction(position);

        }
    }
}
