package world;

public class Tile {
	public static Tile tiles[] = new Tile[255];
	public static byte not = 0;
	
	public static final Tile test_tile = new Tile((byte)0, "grass");
	public static final Tile test_tile2 = new Tile((byte)1, "wall").setSolid();
	
	private byte id;
	private String texture;
	private boolean solid;
	
	public Tile(byte id, String texture) {
		this.id = Tile.not;
		Tile.not++;
		this.texture = texture;
		this.solid = false;
		if(tiles[id] != null)
			throw new IllegalStateException("Tile at: "+ id +" is already being used");
		tiles[id]= this;
	}
	
	public Tile setSolid() { this.solid = true; return this; }
	public boolean isSolid() { return this.solid; }
	
	public byte getId() {
		return this.id;
	}
	
	public String getTexture() {
		return this.texture;
	}
}
