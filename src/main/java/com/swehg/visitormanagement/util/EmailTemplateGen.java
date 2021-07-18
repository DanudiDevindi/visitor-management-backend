package com.swehg.visitormanagement.util;

import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.entity.VisitEntity;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hp
 */
@Component
public class EmailTemplateGen {
    public String getCheckInEmailTemplate(VisitEntity v) {


        Date date = v.getCheckinTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);



        return "<div style=\"width:500px; height: 500px; background-color:#e1f5fe;\">\n" +
                "\n" +
                "<table border=\"0\" style=\"width:500px\">\n" +
                "\t<tbody>\n" +
                "\t<tr>\n" +
                "\t\t\n" +
                "\t\t<div style=\"background-color:#4A8ADF; text-align:right; padding:10px\">\n" +
                "\t\t\n" +
                "\t\t<img src=\"http://lotoraturu.com/wp-content/uploads/2021/07/visito-logo3.png\" width=100>\n" +
                "\t\t\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<div style=\"text-align:center; color:#003768\">\n" +
                "\t\t\t<h1>New Visitor checked in for you!</h1>\n" +
                "\t\t\t<h4>Pass Number: " + v.getPassCardEntity().getName() + "</h4>\n" +
                "\t\t</div>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<table style=\"padding:10px 100px\">\n" +
                "\n" +
                "<tr>\n" +
        "\t\t\t\t<td colspan=\"3\" style=\"background-color:#003768; color:white;\">Checked in details" +
                "</td>\n" +
                "</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Date</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate.split(" ")[0] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Time</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate.split(" ")[1] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Name</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getFirstName() + " "  + v.getVisitorEntity().getLastName() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Mobile</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getMobile() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Email</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getEmail() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Purpose</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getPurpose()+ "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "\t</tr>\n" +
                "\t</tbody>\n" +
                "\t\n" +
                "</table>\n" +
                "\n" +
                "</div>";
    }

    public String getCheckedOut(VisitEntity v) {

        Date date1 = v.getCheckinTime();
        Date date2 = v.getCheckoutTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate1 = dateFormat.format(date1);
        String strDate2 = dateFormat.format(date2);


        return "<div style=\"width:500px; height: 500px; background-color:#e1f5fe;\">\n" +
                "\n" +
                "<table border=\"0\" style=\"width:500px\">\n" +
                "\t<tbody>\n" +
                "\t<tr>\n" +
                "\t\t\n" +
                "\t\t<div style=\"background-color:#4A8ADF; text-align:right; padding:10px\">\n" +
                "\t\t\n" +
                "\t\t<img src=\"http://lotoraturu.com/wp-content/uploads/2021/07/visito-logo3.png\" width=100>\n" +
                "\t\t\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<div style=\"text-align:center; color:#003768\">\n" +
                "\t\t\t<h1>Visitor checked out!</h1>\n" +
                "\t\t\t<h4>Pass Number: " + v.getPassCardEntity().getName() + "</h4>\n" +
                "\t\t</div>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<table style=\"padding:10px 100px\">\n" +
                "\n" +
                "<tr>\n" +
                "\t\t\t\t<td colspan=\"3\" style=\"background-color:#003768; color:white;\">Checked out details" +
                "</td>\n" +
                "</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Date</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate2.split(" ")[0] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Time</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate2.split(" ")[1] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "<tr>\n" +
                "\t\t\t\t<td colspan=\"3\" style=\"background-color:#003768; color:white;\">Checked in details" +
                "</td>\n" +
                "</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Date</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate1.split(" ")[0] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Time</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + strDate1.split(" ")[1] + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Name</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getFirstName() + " "  + v.getVisitorEntity().getLastName() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Mobile</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getMobile() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Email</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getVisitorEntity().getEmail() + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Purpose</td>\n" +
                "\t\t\t\t<td>:</td>\n" +
                "\t\t\t\t<td>" + v.getPurpose()+ "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "\t</tr>\n" +
                "\t</tbody>\n" +
                "\t\n" +
                "</table>\n" +
                "\n" +
                "</div>";
    }
}
