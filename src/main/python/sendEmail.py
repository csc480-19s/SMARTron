import email
import imaplib
import os
import smtplib
import mimetypes
from email.mime.multipart import MIMEMultipart
from email.mime.application import MIMEApplication
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email import encoders
from credentials import username as smartronUsername
from credentials import password as smartronPassword

class SendEmail():
        
    # method that is called when files are recieved by the imapConnect.py script.
    # takes in email address to be sent to
    def sendEmailOnFilesRecieved(emailAddress):
        # print statement for testing purposes
        #print("start")
        fromaddr = 'smartron@cs.oswego.edu'

        # email address the email will be sent to
        toaddrs  = emailAddress
        type(toaddrs)

        #defines structure of email object
        msg = MIMEMultipart()
        msg['Subject'] = 'SMARTron Scans Recieved'
        msg['From'] = fromaddr
        msg['To'] = toaddrs

        # fill in this part with desired message to be sent
        text = "This is a Real Trial Test!"
        part = MIMEText(text, 'plain')

        # attach message to email object
        msg.attach(part)

        # username and password referenced from credentials file 
        # all emails will be sent form smartron account
        username = smartronUsername
        password = smartronPassword
        server = smtplib.SMTP('cs.oswego.edu')
        server.ehlo()
        server.starttls()
        server.login(username,password)
        server.sendmail(fromaddr, toaddrs, msg.as_string())
        server.quit()

        # print statement for testing purposes
        # print("Email sent")

    def sendEmailOnFilesProcessed(emailAddress, csvPath):
        fromaddr = 'smartron@cs.oswego.edu'

        # email address the email will be sent to
        toaddrs  = emailAddress
        type(toaddrs)

        # defines structure of email object
        msg = MIMEMultipart()
        msg['Subject'] = 'SMARTron Scans Processed'
        msg['From'] = fromaddr
        msg['To'] = toaddrs
        attachment = MIMEBase('application', "octet-stream")

        # fill this in with desired file name, must be in the same directory
        filename = csvPath
        attachment.set_payload(open(filename, "rb").read())
        encoders.encode_base64(attachment)
        attachment.add_header('Content-Disposition', 'attachment; filename=%s' % filename)
        msg.attach(attachment)

        # fill in this part with desired message to be sent
        text = "This is a Real Trial Test!"
        part = MIMEText(text, 'plain')

        # attach message to email object
        msg.attach(part)

        # username and password referenced from credentials file
        # all emails will be sent form smartron account
        username = smartronUsername
        password = smartronPassword
        server = smtplib.SMTP('cs.oswego.edu')
        server.ehlo()
        server.starttls()
        server.login(username,password)
        server.sendmail(fromaddr, toaddrs, msg.as_string())
        server.quit()

        # print statement for testing purposes
        # print("Email sent"))

# current main function for testing
class Main(SendEmail):

    def main():

        # fill this in with email address to send to
        SendEmail.sendEmailOnFilesProcessed("ENTER EMAIL HERE", "CSV Path")

    if __name__ == "__main__": main()
