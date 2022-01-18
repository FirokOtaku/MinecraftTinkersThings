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
    // Phantasmic 异象
    // 这个modifier的效果以后会合并到一个套装里
    //
    // 新增日光月光祭坛 含有上面特性的工具可以放到祭坛上回复耐久
    // moon alter 月光祭坛
    // sun alter 日光祭坛
    //
    // 还没做的 modifier
    // frost jack
    // anti effect
    // anger of farmer
    // annihilating
    // anti grav
    // clustering
    // cracking
    // degeneration
    // nature blessing
    // netting
    // parroting
    // ponderous projectile
    // rancher
    // recombining
    // retrospective 不太想做这种的了
    // sourcing
}
