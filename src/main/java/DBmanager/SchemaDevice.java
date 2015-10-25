 package DBmanager;

 import org.joda.time.DateTime;

 /**
 * Created by ueh093 on 10/23/15.
 */
public class SchemaDevice {


     private Integer deviceID;
     private DateTime timePoint;
     private Integer action;
     private DateTime updatedAt;



     public DateTime getTimePoint() {
         return timePoint;
     }

     public void setTimePoint(DateTime timePoint) {
         this.timePoint = timePoint;
     }


     public DateTime getUpdatedAt() {
         return updatedAt;
     }

     public void setUpdatedAt(DateTime updatedAt) {
         this.updatedAt = updatedAt;
     }

     public SchemaDevice(Integer deviceID, DateTime timePoint, Integer action){
        this.deviceID=deviceID; this.timePoint=timePoint; this.action=action;
    }

    public DateTime getDeviceName() {
        return timePoint;
    }
     

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

     public Integer getDeviceID() {
         return deviceID;
     }

     public void setDeviceID(Integer deviceID) {
         this.deviceID = deviceID;
     }


 }
