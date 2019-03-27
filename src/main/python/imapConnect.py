import email
import imaplib
import os
import smtplib
import mimetypes
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase

def sendEmail(emailAddress):
    fromaddr = 'smartron@cs.oswego.edu'

    # email address the email will be sent to
    toaddrs  = emailAddress
    type(toaddrs)
    msg = MIMEMultipart()
    msg['Subject'] = 'Test'
    msg['From'] = fromaddr
    msg['To'] = toaddrs
    text = "files have been recieved and are being processed"
    part = MIMEText(text, 'plain')
    msg.attach(part)
    username = 'smartron'
    password = 'csc480'
    server = smtplib.SMTP('cs.oswego.edu')
    server.ehlo()
    server.starttls()
    server.login(username,password)
    server.sendmail(fromaddr, toaddrs, msg.as_string())
    server.quit()
    print("Email sent")

class FetchEmail():

    connection = None
    error = None

    def __init__(self, mail_server, username, password):
        self.connection = imaplib.IMAP4_SSL(mail_server)
        self.connection.login(username, password)
        # so we can mark mail as read
        self.connection.select(readonly=False) 

    def fetch_unread_messages(self):
        
        # Retrieve unread messages

        # select a folder in the email account
        self.connection.select('inbox')
        result, data = self.connection.search(None, '(NOT SEEN)')
        if result == "OK":
            print('Reading mail...')
            if data[0] == b'':
                print('no new mail')
                self.connection.close()
            email_message = None
            count = 0
            for num in data[0].split():
                typ, data = self.connection.fetch(num, '(RFC822)' )
                raw_email = data[0][1]
                # converts byte literal to string removing b''
                raw_email_string = raw_email.decode('utf-8')
                email_message = email.message_from_string(raw_email_string)
       
                # downloading attachments
                if (email_message is not None):

                    for part in email_message.walk():
                        if part.get_content_maintype() == 'multipart':
                            continue
                        if part.get('Content-Disposition') is None:
                            continue
                        fileName = part.get_filename()
                        if bool(fileName):
                            filePath = os.path.join('Macintosh\ HD/Users/jondntryniski/480_emails', fileName)
                            if not os.path.isfile(filePath) :
                                fp = open(filePath, 'wb')
                                fp.write(part.get_payload(decode=True))
                                fp.close()
                            subject = str(email_message).split("Subject: ", 1)[1].split("\nTo:", 1)[0]
                            print('Downloaded "{file}" from email titled "{subject}" with UID {uid}.'.format(file=fileName, subject=subject, uid=data[-1].decode('utf-8')))
                            count+=1
                        else:
                            self.connection.close()
        else:
            print("Failed to retreive emails.")
            self.connection.close()
        if count > 0:
            sendEmail('ENTER EMAIL ADDRESS HERE')

    
            

class Main(FetchEmail):

    def main():
        
        imap_host = 'cs.oswego.edu'
        imap_user = 'smartron'
        imap_pass = 'csc480'
        download_folder = 'ENTER DOWNLOAD FOLDER DIRECTORY ADDRESS HERE'

        self = FetchEmail(imap_host,imap_user,imap_pass)
        
        if (self):
            print('Connection Successful')
            FetchEmail.fetch_unread_messages(self)
            
    if __name__ == "__main__": main()
