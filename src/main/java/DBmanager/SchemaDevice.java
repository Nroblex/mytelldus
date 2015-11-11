 package DBmanager;

 import org.joda.time.DateTime;

 /**
 * Created by ueh093 on 10/23/15.
 */
public class SchemaDevice  {

     private Integer ID;
     private Integer deviceID;
     private String deviceName;
     private DateTime timePoint;
     private Integer action;
     private DateTime updatedAt;
     private Integer dayOfWeek;


     public Integer getDayOfWeek() {
         return dayOfWeek;
     }

     public void setDayOfWeek(Integer dayOfWeek) {
         this.dayOfWeek = dayOfWeek;
     }

     public Integer getID() {
         return ID;
     }

     public void setID(Integer ID) {
         this.ID = ID;
     }

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

     public SchemaDevice(Integer ID, Integer deviceID, DateTime timePoint, Integer action, Integer dayOfWeek){
        this.ID = ID; this.deviceID=deviceID; this.timePoint=timePoint; this.action=action; this.dayOfWeek=dayOfWeek;
    }


     public String getDeviceName() {
         return deviceName;
     }

     public void setDeviceName(String deviceName) {
         this.deviceName = deviceName;
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
