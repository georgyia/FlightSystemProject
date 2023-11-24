package server.java.root.service;

import root.model.flight.Staff;
import org.springframework.beans.factory.annotation.Autowired;

public class StaffService {

        private final Staff staff;

        @Autowired
        public StaffService(Staff staff) {
            this.staff = staff;
        }
}
