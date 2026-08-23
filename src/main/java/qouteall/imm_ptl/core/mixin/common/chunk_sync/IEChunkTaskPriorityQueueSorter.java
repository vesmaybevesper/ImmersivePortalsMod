package qouteall.imm_ptl.core.mixin.common.chunk_sync;

import net.minecraft.server.level.ChunkTaskDispatcher;
import net.minecraft.util.thread.AbstractConsecutiveExecutor;
import net.minecraft.util.thread.StrictQueue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkTaskDispatcher.class)
public interface IEChunkTaskPriorityQueueSorter {
    @Accessor("mailbox")
    // Class not 1-to-1 could be wrong check later
    AbstractConsecutiveExecutor<StrictQueue.RunnableWithPriority> ip_getMailBox();
}
