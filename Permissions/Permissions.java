package dev.chromenetwork.prison.Permissions;

public enum Permissions {
	MINES_ADMIN("chromenetwork.prison.mines.admin");
	
	private String permission;
	Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
}
