// 三套建材的多方块结构支持暂缓，保留 ASM 补丁源码以便恢复。
/*
package firok.tiths.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class SearedStructureTransformer implements IClassTransformer
{
    private static final String SMELTERY = "slimeknights/tconstruct/smeltery/TinkerSmeltery";
    private static final String MULTIBLOCK = "slimeknights.tconstruct.smeltery.multiblock.";
    private static final String HOOK = "firok/tiths/common/SearedBuildingMaterials";
    private static final String BLOCK_PAIR = "(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)Z";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytecode)
    {
        if (bytecode == null || !isStructure(transformedName)) return bytecode;

        ClassNode structure = new ClassNode();
        new ClassReader(bytecode).accept(structure, 0);
        boolean patched = false;
        for (MethodNode method : structure.methods)
        {
            for (AbstractInsnNode insn : method.instructions.toArray())
            {
                if (!(insn instanceof FieldInsnNode) || insn.getOpcode() != Opcodes.GETSTATIC) continue;
                FieldInsnNode field = (FieldInsnNode) insn;
                if (!SMELTERY.equals(field.owner) || !("searedBlock".equals(field.name) || "searedTank".equals(field.name)))
                    continue;

                // Keep the native branch and its stack frames. Only replace the identity predicate.
                AbstractInsnNode next = field.getNext();
                while (next != null && next.getOpcode() < 0) next = next.getNext();
                if (!(next instanceof JumpInsnNode)) continue;
                int comparison = next.getOpcode();
                if (comparison != Opcodes.IF_ACMPEQ && comparison != Opcodes.IF_ACMPNE) continue;
                method.instructions.insert(field, new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK,
                        "matchesStructureBlock", BLOCK_PAIR, false));
                method.instructions.set(next, new JumpInsnNode(
                        comparison == Opcodes.IF_ACMPEQ ? Opcodes.IFNE : Opcodes.IFEQ, ((JumpInsnNode) next).label));
                patched = true;
            }
        }
        if (!patched) throw new IllegalStateException("Cannot extend matching structure blocks in " + transformedName);
        ClassWriter writer = new ClassWriter(0);
        structure.accept(writer);
        return writer.toByteArray();
    }

    private static boolean isStructure(String name)
    {
        return (MULTIBLOCK + "MultiblockSmeltery").equals(name)
                || (MULTIBLOCK + "MultiblockSearedFurnace").equals(name)
                || (MULTIBLOCK + "MultiblockTinkerTank").equals(name)
                || "slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructureFuelTank".equals(name);
    }
}
*/
