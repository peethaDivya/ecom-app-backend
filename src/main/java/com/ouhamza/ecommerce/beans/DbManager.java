package com.ouhamza.ecommerce.beans;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DbManager {

    // Statement stmt = null;
    // Connection con = null;
    //ResultSet rs;
    PreparedStatement pst1 = null;
    Statement st = null;
    //public ResourceSuplier resourcesuplier;
    String driverclass;
    String JDBCUrl;
    String appacheaddress;

    public DbManager(int whichdb) {
        //resourcesuplier = ResourceSuplier.getResourceSuplierObject();
           
       
        //Connect to LMS
        if(whichdb == 0) {
            driverclass = "com.mysql.cj.jdbc.Driver";//resourcesuplier.getDriverclass("mysql");
           // jdbc:mysql://localhost:3306/full-stack-ecommerce?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC
            JDBCUrl = "jdbc:mysql://ecommerce.cthx8hdjqzlm.us-east-2.rds.amazonaws.com:3306/ecommerce?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";//resourcesuplier.getJDBCUrl();
        }
        else {}
       
        try {
            Class.forName(driverclass);
        } catch (Exception e) {
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            //jdbc:derby://ELUSERVER:1527/CMSDB;create=true;user=me;password=mine'
            //con = DriverManager.getConnection("jdbc:derby://JK:1527/CMSDB;create=true;user=me;password=mine");
            //con = DriverManager.getConnection("jdbc:mysql://10.0.0.10:3306/lms","root","elucido");
            if(JDBCUrl.contains("derby")) {
                con = DriverManager.getConnection(JDBCUrl);
            }else if(JDBCUrl.contains("mysql")) {
                String myUser = "ecommerce";//resourcesuplier.getUser();
                String myPassword = "12345678" ;//resourcesuplier.getPassword();
                con = DriverManager.getConnection(JDBCUrl, myUser, myPassword);
            }
            //con = DriverManager.getConnection(JDBCUrl);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public Statement getStatement() {
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            stmt.getFetchSize();
            stmt.setFetchSize(125);
        } catch (Exception e) {
            System.out.println("it's in Statement" + e);
        }
        return stmt;
    }


    public void update_row(String query) {
        Connection con = null;
        try {
            con = getConnection();
            st =  con.createStatement();
            st.addBatch(query);
            st.executeBatch();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }


    public int update(String query, String[] u1) {
        int j = 0;
        Connection con = null;
        try {
            con = getConnection();

            pst1 = con.prepareStatement(query);
            for (int i = 0; i < u1.length; i++) {
                pst1.setString(i + 1, u1[i]);
            }



            j = pst1.executeUpdate();
           
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                if (pst1 != null) {
                    pst1.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return j;
    }



    public ResultSet getResultSet(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = getStatement();
            rs = stmt.executeQuery(query);
            rs.getFetchSize();
            rs.setFetchSize(125);
        } catch (Exception e) {
            System.out.println("it's in resultset" + e);
        } finally {
        }
        return rs;
    }

    public static void main(String args[]) {
        DbManager db = new DbManager(0);
        Connection con1 = db.getConnection();
        //   System.out.println("Connection  :"+con1);
        Statement st = db.getStatement();
        //   System.out.println("Statement"+st);
        ResultSet r = db.getResultSet("select * from mdl_user");
        //cleanResource(db);
         System.out.println("ResultSet"+r);
        //   System.out.println("Connection  :"+con1);
        //  System.out.println("Statement"+st);
        //  System.out.println("ResultSet"+r);
    }
     public String insertScheduleinfo(String query, String sdate, String stime, String etime, String edate, String playlistname, String playerid, String moddate, String mac_address,String overridemode, String groupName) {
        String result = "1";
        Statement smt1;
        Statement smt;
        ResultSet rs1;
        try {
            smt1 = getStatement();
            smt = getStatement();
            String Test_update = "delete from schedulemaster  where stdate <'" + moddate + "' and edate<'" + moddate + "'";
            //   System.out.println("Test update : " + Test_update);

            smt.execute(Test_update);

            rs1 = smt1.executeQuery("select * from schedulemaster where mac_address='" + mac_address + "'and  stdate BETWEEN '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "' OR mac_address='" + mac_address + "' AND edate between '" + sdate + "' AND '" + edate + "'and  '" + stime + "'>=stime and '" + etime + "'<=etime OR mac_address='" + mac_address + "' AND edate between '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "'");
          //  System.out.println("select * from schedulemaster where mac_address='" + mac_address + "'and  stdate BETWEEN '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "' OR mac_address='" + mac_address + "' AND edate between '" + sdate + "' AND '" + edate + "'and  '" + stime + "'<=stime and '" + etime + "'<=etime");
            if (rs1.next()) {


                String sid=rs1.getString("sid");
                String override=rs1.getString("override");
                if(override.equals("yes"))
                {
                 String query4="Update schedulemaster set STDATE='"+sdate+"',STIME='"+stime+"',ETIME='"+etime+"',EDATE='"+edate+"',playlistname='"+playlistname+"',MODDATE='"+moddate+"',override='"+overridemode+"' where sid="+sid+"";
                updateScheduleinfo(sid,  query4, sdate, stime, etime, edate, playlistname, moddate,  mac_address,  groupName);
              result="0";  }
                else{
                result = "0";}

            } else {
                String ne = mac_address.replace(":", "");
            //    File f1= new File(appacheaddress + groupName + "/Players/" + ne + "/schedule");
//f1.createNewFile();
                PrintWriter pw = new PrintWriter(new FileOutputStream(appacheaddress + groupName + "/Players/" + ne + "/schedule"));
                pw.println("");
                //clean up
                pw.close();

                Connection con = getConnection();

                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, sdate);
                pst.setString(2, stime);
                pst.setString(3, etime);

                pst.setString(4, edate);
                pst.setString(5, playlistname);
                pst.setString(6, playerid);
                pst.setString(7, moddate);
                pst.setString(8, mac_address);
                pst.setString(9, overridemode);

                pst.executeUpdate();
                result = "1";

                try {
                    if (pst != null) {
                        pst.close();
                        con.close();
                    }
                } catch (Exception e) {
                  //  System.out.println("er " + e);
                }
                ResultSet resultSet = null;
                try {
                    Calendar c = Calendar.getInstance();
                    // String

                    String DATE_FORMAT = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    resultSet = getResultSet("select * from schedulemaster where mac_address='" + mac_address + "'");
                    while (resultSet.next()) {
                        String stdate = resultSet.getString("stdate");
                        String stTime = resultSet.getString("stime");
                        String eTime = resultSet.getString("etime");
                        String eDate = resultSet.getString("edate");
                        String playlistName = resultSet.getString("playlistname");
                        c.setTime((Date) sdf.parse(eDate));
                        Calendar c1 = Calendar.getInstance(); //
                        c1.setTime((Date) sdf.parse(stdate));
                        double tempstTime = 0.0;

                        double tempeTime = 0.0;

                        try {
                            tempstTime = Double.parseDouble(stTime);
                            tempeTime = Double.parseDouble(eTime);
                        } catch (Exception e) {
                        }
                        //   System.out.println(st1);
                        double t = (tempstTime - (int) tempstTime) * 100;
                        double q = tempstTime - (tempstTime - (int) tempstTime);
                        t = Math.floor(t);
                        q = Math.floor(q);
                        double t1 = (tempeTime - (int) tempeTime) * 100;
                        double q1 = tempeTime - (tempeTime - (int) tempeTime);
                        t1 = Math.floor(t1);
                        q1 = Math.floor(q1);






                        try {
                            // Create file



                            FileWriter fstream = new FileWriter(appacheaddress + groupName + "/Players/" + ne + "/schedule", true);
                            BufferedWriter fout = new BufferedWriter(fstream);








if(c.equals(c1))
{
    // System.out.println(c1.getTime());

                                int dat = c1.get(Calendar.DATE);
                                int mont = (c1.get(Calendar.MONTH));
                                mont=mont+1;
                                fout.write((int) t + " " + (int) q + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayStart " + playlistName);

                                fout.newLine();
                                  fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
 fout.newLine();




}
int m=0;
                            while (c.after(c1)) {

                              if(m!=0)
                              {
                                    c1.add(Calendar.DATE, +1);
                              }
                                // c.setTime(new Date());
                                //Then I want the day on 14th Nov 2004



                              //  System.out.println(c1.getTime());

                                int dat = c1.get(Calendar.DATE);
                                int mont = (c1.get(Calendar.MONTH));
                                mont=mont+1;
                                fout.write((int) t + " " + (int) q + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayStart " + playlistName);
                                fout.newLine();
                                  fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
 fout.newLine();
m++;
                            //System.out.println("day=" + day);
                            }

                            int dat = c1.get(Calendar.DATE);
                            int mont = (c1.get(Calendar.MONTH));
                             mont=mont+1;
                         //   fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
                          //  fout.newLine();

                            //Close the output stream
                            fout.close();
                            fstream.close();
                        } catch (Exception e) {//Catch exception if any
                          //  System.err.println("Error: " + e.getMessage());
                        }


                        //Xmlrpc.apply_Schedule(getip(mac_address), ne);
                    }
                } catch (Exception ex) {
                    //System.out.println(ex);
                   // ex.printStackTrace();

                } finally {
                    try {
                        if (resultSet != null) {
                            Statement tempstmt = resultSet.getStatement();
                            Connection tempcon = tempstmt.getConnection();
                            resultSet.close();
                            tempstmt.close();
                            tempcon.close();

                        }
                    } catch (Exception e) {
                       // System.out.println(e);
                    }
                }



            }
            try {
                if (smt1 != null) {
                    Connection dcon = smt1.getConnection();
                    if (rs1 != null) {
                        rs1.close();
                    }
                    smt1.close();
                    dcon.close();

                }
                if (smt != null) {
                    Connection dcon = smt1.getConnection();
                    smt.close();
                    dcon.close();
                }

            } catch (Exception t) {
            }




        } catch (Exception e) {
            //System.out.println(e);
        }
        return result;
    }

 public String updateScheduleinfo(String sid, String query, String sdate, String stime, String etime, String edate, String playlistname, String moddate, String mac_address, String groupName) {
        String result = "1";
        Statement smt1;
        ResultSet rs1;
        try {
            smt1 = getStatement();
            System.out.println("select mac_address from schedulemaster where sid!=" + sid + " and mac_address='" + mac_address + "'and  stdate BETWEEN '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "'and override='no' OR mac_address='" + mac_address + "' AND edate between '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "'");

            rs1 = smt1.executeQuery("select mac_address from schedulemaster where sid!=" + sid + " and mac_address='" + mac_address + "'and  stdate BETWEEN '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "' OR sid!=" + sid + " and mac_address='" + mac_address + "' AND edate between '" + sdate + "' AND '" + edate + "'and  stime>='" + stime + "' and etime<='" + etime + "'");

            if (rs1.next()) {
                result = "0";

            } else {
                String ne = mac_address.replace(":", "");
                PrintWriter pw = new PrintWriter(new FileOutputStream(appacheaddress + groupName + "/Players/" + ne + "/schedule"));
                pw.println("");
                //clean up
                pw.close();

                Connection con = getConnection();

                Statement pst = con.createStatement();

               // System.out.println(query);

                pst.execute(query);
                result = "1";

                try {
                    if (pst != null) {
                        pst.close();
                        con.close();
                    }
                } catch (Exception e) {
                    //System.out.println("er " + e);
                }
                ResultSet resultSet = null;
                try {
                    Calendar c = Calendar.getInstance();
                    // String

                    String DATE_FORMAT = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    resultSet = getResultSet("select * from schedulemaster where mac_address='" + mac_address + "'");
                    while (resultSet.next()) {
                        String stdate = resultSet.getString("stdate");
                        String stTime = resultSet.getString("stime");
                        String eTime = resultSet.getString("etime");
                        String eDate = resultSet.getString("edate");
                        String playlistName = resultSet.getString("playlistname");
                        c.setTime((Date) sdf.parse(eDate));
                        Calendar c1 = Calendar.getInstance(); //
                        c1.setTime((Date) sdf.parse(stdate));
                        double tempstTime = 0.0;

                        double tempeTime = 0.0;

                        try {
                            tempstTime = Double.parseDouble(stTime);
                            tempeTime = Double.parseDouble(eTime);
                        } catch (Exception e) {
                        }
                        //   System.out.println(st1);
                        double t = (tempstTime - (int) tempstTime) * 100;
                        double q = tempstTime - (tempstTime - (int) tempstTime);
                        t = Math.floor(t);
                        q = Math.floor(q);
                        double t1 = (tempeTime - (int) tempeTime) * 100;
                        double q1 = tempeTime - (tempeTime - (int) tempeTime);
                        t1 = Math.floor(t1);
                        q1 = Math.floor(q1);






                        try {
                            // Create file


                            FileWriter fstream = new FileWriter(appacheaddress + groupName + "/Players/" + ne + "/schedule", true);
                            BufferedWriter fout = new BufferedWriter(fstream);



if(c.equals(c1))
{
    // System.out.println(c1.getTime());

                                int dat = c1.get(Calendar.DATE);
                                int mont = (c1.get(Calendar.MONTH));
                                mont=mont+1;
                                fout.write((int) t + " " + (int) q + " " + dat + " " + mont+ " * " + "/etc/configs/timed_play/PlayStart " + playlistName);
                                fout.newLine();
                                  fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
 fout.newLine();




}




                          int m=0;
                            while (c.after(c1)) {

                              if(m!=0)
                              {
                                    c1.add(Calendar.DATE, +1);
                              }
                                // c.setTime(new Date());
                                //Then I want the day on 14th Nov 2004



                               // System.out.println(c1.getTime());

                                int dat = c1.get(Calendar.DATE);
                                int mont = (c1.get(Calendar.MONTH));
                                mont=mont+1;
                                fout.write((int) t + " " + (int) q + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayStart " + playlistName);
                                fout.newLine();
                                  fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
 fout.newLine();

m++;
                            //System.out.println("day=" + day);
                            }
                           // int dat = c1.get(Calendar.DATE);
                          //  int mont = (c1.get(Calendar.MONTH));
                            //  mont=mont+1;
                           // fout.write((int) t1 + " " + (int) q1 + " " + dat + " " + mont + " * " + "/etc/configs/timed_play/PlayEnd " + playlistName);
                           // fout.newLine();

                            //Close the output stream
                            fout.close();
                            fstream.close();
                        } catch (Exception e) {//Catch exception if any
                            //System.err.println("Error: " + e.getMessage());
                        }
                       // Xmlrpc.apply_Schedule(getip(mac_address), ne);
                    }
                } catch (Exception ex) {
                  //  System.out.println(ex);

                } finally {
                    try {
                        if (resultSet != null) {
                            Statement tempstmt = resultSet.getStatement();
                            Connection tempcon = tempstmt.getConnection();
                            resultSet.close();
                            tempstmt.close();
                            tempcon.close();

                        }
                    } catch (Exception e) {
                    }
                }



            }
            try {
                if (smt1 != null) {
                    Connection dcon = smt1.getConnection();
                    if (rs1 != null) {
                        rs1.close();
                    }
                    dcon.close();
                }
            } catch (Exception t) {
            }




        } catch (Exception e) {
           // System.out.println(e);
        }
        return result;
    }
 public String getip(String mac) {
        Connection con1 = null;
        Statement stmt = null;
        ResultSet rs2 = null;
        String msg = "";
        try {
            con1 = getConnection();
            stmt = con1.createStatement();
            rs2 = stmt.executeQuery("select port,public_ip_address from emn_device_list where mac_address='" + mac + "'");
            if (rs2.next()) {
                msg = rs2.getString("port");
                String msg2 = rs2.getString("public_ip_address");
                //System.out.println(msg2);
                msg = msg2 + ":" + msg;
               // System.out.println(msg);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs2 != null) {
                    rs2.close();
                }
            } catch (Exception e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
            }
            try {
                if (con1 != null) {
                    con1.close();
                }
            } catch (Exception e) {
            }
        }
        return msg;
    }

    public int insertItems(String query, String item[],String auto_field) {
        int j = 0;
        try {
            Connection con = getConnection();
            String generated_columns[] = {auto_field};
            PreparedStatement pst = con.prepareStatement(query,generated_columns);
            for (int i = 0; i < item.length; i++) {
                pst.setString(i + 1, item[i]);
            }
            //System.out.println(pst);
           
            j = pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()) {
            long id = rs.getLong(1);
            System.out.println(id);
            j = (int)id;
            }
            //System.out.println(j);
            try {
                if (pst != null) {
                    pst.close();
                    con.close();
                }
            } catch (Exception e) {
                  System.out.println("er " + e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return j;
    }

    public void delete(String query)//delete from assetsmaster where aid="+aid+" "
    {

        Statement stmt = getStatement();
        try {
            stmt.execute(query);
        } catch (SQLException ex) {
           
        } finally {
            if (stmt != null) {
                try {
                    Connection tempcon = stmt.getConnection();
                    stmt.close();
                    tempcon.close();
                } catch (SQLException ex) {
                   
                }
            }
        }
    }
}
