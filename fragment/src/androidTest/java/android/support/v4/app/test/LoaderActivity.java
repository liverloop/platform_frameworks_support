/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4.app.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.fragment.test.R;
import android.support.testutils.RecreatedActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoaderActivity extends RecreatedActivity
        implements LoaderManager.LoaderCallbacks<String> {
    private static final int TEXT_LOADER_ID = 14;

    public TextView textView;
    public TextView textViewB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loader);
        textView = findViewById(R.id.textA);
        textViewB = findViewById(R.id.textB);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, new TextLoaderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(TEXT_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new TextLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        textView.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    static class TextLoader extends AsyncTaskLoader<String> {
        TextLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            return "Loaded!";
        }
    }

    public static class TextLoaderFragment extends Fragment
            implements LoaderManager.LoaderCallbacks<String> {
        public TextView textView;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getLoaderManager().initLoader(TEXT_LOADER_ID, null, this);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_c, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            textView = view.findViewById(R.id.textC);
        }

        @NonNull
        @Override
        public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
            return new TextLoader(getContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String data) {
            textView.setText(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<String> loader) {
        }
    }
}
