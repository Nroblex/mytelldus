 package DBmanager;

 import org.joda.time.DateTime;

 /**
 * Created by ueh093 on 10/23/15.
 */
public class SchemaDevice {

     private Integer ID;
     private Integer deviceID;
     private String deviceName;
     private DateTime timePoint;
     private String action;
     private DateTime updatedAt;
     private Integer dayOfWeek;
     private String nameOfWeekday;

     public SchemaDevice() {

     }


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

     public SchemaDevice(Integer ID, Integer deviceID, String deviceName, DateTime timePoint, String action, Integer dayOfWeek){
        this.ID = ID; this.deviceID=deviceID; this.deviceName = deviceName; this.timePoint=timePoint; this.action=action; this.dayOfWeek=dayOfWeek;
    }

     public SchemaDevice(Integer deviceID, DateTime timePoint, String action){
         this.deviceID=deviceID; this.timePoint=timePoint; this.action=action;
     }

     public String getDeviceName() {
         if (deviceName==null){
             deviceName="apa";
         }
         return deviceName;
     }

     public void setDeviceName(String deviceName) {
         this.deviceName = deviceName;
     }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

     public Integer getDeviceID() {
         return deviceID;
     }

     public void setDeviceID(Integer deviceID) {
         this.deviceID = deviceID;
     }

     public String getNameOfWeekday() {
         return nameOfWeekday;
     }

     public void setNameOfWeekday(String nameOfWeekday) {
         this.nameOfWeekday = nameOfWeekday;
     }
 }
