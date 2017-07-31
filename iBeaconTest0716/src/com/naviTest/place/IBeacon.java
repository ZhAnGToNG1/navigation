package com.naviTest.place;

public class IBeacon {
	private String name;
	private String major;
	private String minor;
	private String proximityUuid;
	private String bluetoothAddress;
	private String txPower;
	private int rssi;

	public IBeacon() {
	}

	public IBeacon(String str) {
		StringBuffer strbuf = new StringBuffer(str);
		strbuf = strbuf.deleteCharAt(0);
		// System.out.println(strbuf.toString());

		this.name = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.major = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.minor = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.proximityUuid = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.bluetoothAddress = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.txPower = strbuf.substring(0, strbuf.indexOf("$"));
		strbuf.delete(0, strbuf.indexOf("$") + 1);
		this.rssi = Integer.parseInt(strbuf.substring(0, strbuf.indexOf("$")));
		strbuf.delete(0, strbuf.indexOf("$") + 1);

		// System.out.println("name:"+ name);
		// System.out.println("major:" + major);
		// System.out.println("minor:" + minor);
		// System.out.println("proximityUuid:" + proximityUuid);
		System.out.println("bluetoothAddress:" + bluetoothAddress);
		// System.out.println("txPower:" + txPower);
		System.out.println("rssi:" + rssi);
	}

	@Override
	public String toString() {
		return "[" + this.name + "$" + this.major + "$" + this.minor + "$" + this.proximityUuid + "$"
				+ this.bluetoothAddress + "$" + this.txPower + "$" + this.rssi + "$" + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getProximityUuid() {
		return proximityUuid;
	}

	public void setProximityUuid(String proximityUuid) {
		this.proximityUuid = proximityUuid;
	}

	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}

	public String getTxPower() {
		return txPower;
	}

	public void setTxPower(String txPower) {
		this.txPower = txPower;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

}
