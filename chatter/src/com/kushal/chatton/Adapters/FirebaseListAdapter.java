package com.kushal.chatton.Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;


public abstract class FirebaseListAdapter<T> extends BaseAdapter {

    private Query ref;
    private Class<T> modelClass;
    private int layout;
    private LayoutInflater inflater;
    private List<T> models;
    private Map<String, T> modelNames;
    private ChildEventListener listener;

    public FirebaseListAdapter(Query ref, Class<T> modelClass, int layout, Activity activity) {
        this.ref = ref;
        this.modelClass = modelClass;
        this.layout = layout;
        inflater = activity.getLayoutInflater();
        models = new ArrayList<T>(); //chats
        modelNames = new HashMap<String, T>(); //child name and chats
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        listener = this.ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            	
                T model = dataSnapshot.getValue(FirebaseListAdapter.this.modelClass);
                modelNames.put(dataSnapshot.getName(), model);
                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) {
                    models.add(0, model);
                } 
                else {
                    T previousModel = modelNames.get(previousChildName);
                    int previousIndex = models.indexOf(previousModel);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == models.size()) 
                    {
                       models.add(model);
                    }
                    
                    else
                    {
                        models.add(nextIndex, model);
                    }

                }

                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            	
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            
            }

			@Override
			public void onCancelled(FirebaseError arg0) {}
        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our listener and forget about all of the models
        ref.removeEventListener(listener);
        models.clear();
        modelNames.clear();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }

        T model = models.get(i);
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the layout and modelClass given to the constructor of this class.
     *
     * Your implementation should populate the view using the data contained in the model.
     * @param v The view to populate
     * @param model The object containing the data used to populate the view
     */
    protected abstract void populateView(View v, T model);
}
