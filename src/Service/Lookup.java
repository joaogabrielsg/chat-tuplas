package Service;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.rmi.RemoteException;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceID;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.discovery.DiscoveryEvent;
import net.jini.discovery.DiscoveryListener;
import net.jini.discovery.LookupDiscovery;

public class Lookup implements DiscoveryListener {
    private ServiceTemplate theTemplate;
    private LookupDiscovery theDiscoverer;
    private Object theProxy;

    public Lookup(Class var1) {
        Class[] var2 = new Class[]{var1};
        this.theTemplate = new ServiceTemplate((ServiceID)null, var2, (Entry[])null);
    }

    public Object getService() {
        synchronized(this) {
            if (this.theDiscoverer == null) {
                try {
                    this.theDiscoverer = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
                    this.theDiscoverer.addDiscoveryListener(this);
                } catch (IOException var4) {
                    System.err.println("Failed to init lookup");
                }
            }
        }

        return this.waitForProxy();
    }

    void terminate() {
        synchronized(this) {
            if (this.theDiscoverer != null) {
                this.theDiscoverer.terminate();
            }

        }
    }

    private Object waitForProxy() {
        synchronized(this) {
            while(this.theProxy == null) {
                try {
                    this.wait();
                } catch (InterruptedException var4) {
                }
            }

            return this.theProxy;
        }
    }

    private void signalGotProxy(Object var1) {
        synchronized(this) {
            if (this.theProxy == null) {
                this.theProxy = var1;
                this.notify();
            }

        }
    }

    public void discovered(DiscoveryEvent var1) {
        synchronized(this) {
            if (this.theProxy != null) {
                return;
            }
        }

        ServiceRegistrar[] var2 = var1.getRegistrars();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            ServiceRegistrar var4 = var2[var3];
            Object var5 = null;

            try {
                var5 = var4.lookup(this.theTemplate);
                if (var5 != null) {
                    this.signalGotProxy(var5);
                    break;
                }
            } catch (RemoteException var7) {
                System.err.println("ServiceRegistrar barfed");
                var7.printStackTrace(System.err);
            }
        }

    }

    public void discarded(DiscoveryEvent var1) {
    }
}
