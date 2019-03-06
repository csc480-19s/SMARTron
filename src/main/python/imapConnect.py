import email
import imaplib
import os

class FetchEmail():

    connection = None
    error = None

    def __init__(self, mail_server, username, password):
        self.connection = imaplib.IMAP4_SSL(mail_server)
        self.connection.login(username, password)
        self.connection.select(readonly=False) # so we can mark mail as read

    def fetch_unread_messages(self):
        """
        Retrieve unread messages
        """

        #select a folder in the email account
        self.connection.select('inbox')
        result, data = self.connection.search(None, '(UNSEEN)')
        if result == "OK":
            print('Reading mail')
            print(len(data))
            email_message = None
            for num in data[0].split():
                typ, data = self.connection.fetch(num, '(RFC822)' )
                raw_email = data[0][1]
                # converts byte literal to string removing b''
                raw_email_string = raw_email.decode('utf-8')
                email_message = email.message_from_string(raw_email_string)
        else:
            print("Failed to retreive emails.")
            self.connection.close()
        # downloading attachments
        if (email_message is not None):

            for part in email_message.walk():
                if part.get_content_maintype() == 'multipart':
                    continue
                if part.get('Content-Disposition') is None:
                    continue
                fileName = part.get_filename()
                print(fileName)
                if bool(fileName):
                    filePath = os.path.join('C:/Users/lilse/Downloads', fileName)
                    if not os.path.isfile(filePath) :
                        fp = open(filePath, 'wb')
                        fp.write(part.get_payload(decode=True))
                        fp.close()
                    subject = str(email_message).split("Subject: ", 1)[1].split("\nTo:", 1)[0]
                    print('Downloaded "{file}" from email titled "{subject}" with UID {uid}.'.format(file=fileName, subject=subject, uid=data[-1].decode('utf-8')))
                else:
                    self.connection.close()

class Main(FetchEmail):

    def main():
        
        imap_host = 'cs.oswego.edu'
        imap_user = 'smartron'
        imap_pass = 'csc480'
        download_folder = 'C:/Users/lilse/Downloads'

        self = FetchEmail(imap_host,imap_user,imap_pass)
        print(self)
        if (self):
            print('Connection Successful')
        FetchEmail.fetch_unread_messages(self)
            
    if __name__ == "__main__": main()
