package dev.chromenetwork.prison;

import java.lang.reflect.InvocationHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableListSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMultimapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableSetSerializer;
import dev.chromenetwork.prison.Managers.Managers;


public class Main extends JavaPlugin {
	public static void main(String[] args) {}
	
	public static Main Plugin;
	public static Managers Managers;
	public static WorldEditPlugin WorldEdit;
	
	@Override
	public void onEnable() {
		Plugin = this;
		Managers = new Managers();
		WorldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		
		//Register this packet
		//RemovePrisonToolEnchantments.get().init();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public Kryo getSerializer() {
		Kryo kryo = new Kryo();
		((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
		kryo.register( Arrays.asList( "" ).getClass(), new ArraysAsListSerializer() );
		kryo.register( Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer() );
		kryo.register( Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer() );
		kryo.register( Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer() );
		kryo.register( Collections.singletonList( "" ).getClass(), new CollectionsSingletonListSerializer() );
		kryo.register( Collections.singleton( "" ).getClass(), new CollectionsSingletonSetSerializer() );
		kryo.register( Collections.singletonMap( "", "" ).getClass(), new CollectionsSingletonMapSerializer() );
		kryo.register( GregorianCalendar.class, new GregorianCalendarSerializer() );
		kryo.register( InvocationHandler.class, new JdkProxySerializer() );
		UnmodifiableCollectionsSerializer.registerSerializers( kryo );
		SynchronizedCollectionsSerializer.registerSerializers( kryo );
		ImmutableListSerializer.registerSerializers( kryo );
		ImmutableSetSerializer.registerSerializers( kryo );
		ImmutableMapSerializer.registerSerializers( kryo );
		ImmutableMultimapSerializer.registerSerializers( kryo );
		return kryo;
	}
}