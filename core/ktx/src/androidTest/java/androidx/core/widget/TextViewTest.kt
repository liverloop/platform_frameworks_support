/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.core.widget

import android.widget.TextView
import androidx.test.InstrumentationRegistry
import androidx.test.annotation.UiThreadTest
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
@SmallTest
class TextViewTest {

    private val context = InstrumentationRegistry.getContext()
    private val view = TextView(context)

    @UiThreadTest
    @Test fun doBeforeTextChanged() {
        val called = AtomicBoolean()
        view.doBeforeTextChanged { _, _, _, _ ->
            called.set(true)
        }

        view.text = "text"

        assertTrue(called.get())
    }

    @UiThreadTest
    @Test fun doOnTextChanged() {
        val called = AtomicBoolean()
        view.doOnTextChanged { _, _, _, _ ->
            called.set(true)
        }

        view.text = "text"

        assertTrue(called.get())
    }

    @UiThreadTest
    @Test fun doAfterTextChanged() {
        val called = AtomicBoolean()
        view.doAfterTextChanged { _ ->
            called.set(true)
        }

        view.text = "text"

        assertTrue(called.get())
    }
}
