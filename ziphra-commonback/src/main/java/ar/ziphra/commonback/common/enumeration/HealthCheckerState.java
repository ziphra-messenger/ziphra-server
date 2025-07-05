package ar.ziphra.commonback.common.enumeration;
public enum HealthCheckerState {
	ONLINE (true),
	OFFLINE (false);

    private final boolean online;

    HealthCheckerState(boolean value) 
    {
      this.online = value;
    }

    public boolean isOnline()
    {
      return this.online;
    }
    

}