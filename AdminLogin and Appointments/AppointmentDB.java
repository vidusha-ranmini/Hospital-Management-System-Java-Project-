import java.util.ArrayList;
import java.util.List;

public class AppointmentDB {
    private static List<Appointment> appointments = new ArrayList<>();
    private static int idCounter = 1;

    public static List<Appointment> getAllAppointments() {
        return appointments;
    }

    public static void addAppointment(Appointment appointment) {
        appointment.setId(idCounter++);
        appointments.add(appointment);
    }

    public static void updateAppointment(Appointment updatedAppointment) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == updatedAppointment.getId()) {
                appointment.setPatientName(updatedAppointment.getPatientName());
                appointment.setDoctorName(updatedAppointment.getDoctorName());
                appointment.setDate(updatedAppointment.getDate());
                appointment.setTime(updatedAppointment.getTime());
                break;
            }
        }
    }

    public static void deleteAppointment(int id) {
        appointments.removeIf(appointment -> appointment.getId() == id);
    }
}
