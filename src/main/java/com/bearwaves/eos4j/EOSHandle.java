package com.bearwaves.eos4j;

// A pointer handle to some opaque type within the SDK.
public final class EOSHandle {
    final long ptr;

    EOSHandle(long ptr) {
        this.ptr = ptr;
    }
}
