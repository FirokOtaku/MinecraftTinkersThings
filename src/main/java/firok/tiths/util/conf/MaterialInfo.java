package firok.tiths.util.conf;

/**
 * 材料属性
 */
public class MaterialInfo
{
	public String name;
	public Boolean disable;

	public String[] traits_tool;
	public String[] traits_bow;
	public String[] traits_armor;

	public Boolean disableHead;
	public Integer head_durability;
	public Float head_mining_speed;
	public Float head_attack;
	public Byte head_harvest_level;
	public String[] head_traits;

	public Boolean disableHandle;
	public Float handle_modifier;
	public Integer handle_durability;
	public String[] handle_traits;

	public Boolean disableExtra;
	public Integer extra_durability;
	public String[] extra_traits;

	public Boolean disableBow;
	public Float bow_draw_speed;
	public Float bow_range;
	public Float bow_bonus_damage;
	public String[] bow_traits;

	public Boolean disableString;
	public Float string_modifier;
	public String[] string_traits;

	public Boolean disableFletching;
	public Float fletching_accuracy;
	public Float fletching_modifier;
	public String[] fletching_traits;

	public Boolean disableShaft;
	public Float shaft_modifier;
	public Integer shaft_bonus_ammo;
	public String[] shaft_traits;

	public Boolean disableCore;
	public Float core_durability;
	public Float core_dense;
	public String[] core_traits;

	public Boolean disableTrim;
	public Float trim_durability;
	public String[] trim_traits;

	public Boolean disablePlate;
	public Float plate_modifier;
	public Float plate_durability;
	public Float plate_toughness;
	public String[] plate_traits;
}
