package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import slimeknights.tconstruct.library.modifiers.Modifier;

@DevUse
public class ModifierTest extends Modifier {
    public ModifierTest() {
        super(0xFFD359);
    }

    // 部分 modifier 重做:
    // moon power -> moon glimmering
    // sun power -> sun glimmering
    // 降低回复量
    //
    // 新增日光月光祭坛 含有上面特性的工具可以放到祭坛上回复耐久
    // moon alter 月光祭坛
    // sun alter 日光祭坛
}
