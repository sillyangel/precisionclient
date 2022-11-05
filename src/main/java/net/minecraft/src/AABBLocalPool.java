package net.minecraft.src;

final class AABBLocalPool extends ThreadLocal {
    private AABBPool createNewDefaultPool() {
        return new AABBPool(300, 2000);
    }

    protected Object initialValue() {
        return this.createNewDefaultPool();
    }
}
