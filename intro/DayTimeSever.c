#include "unp.h"
#include <time.h>

int main(int argc, char **argv)
{
	char temp='n';
	int listenfd, connfd;
	struct sockaddr_in servaddr;//address structure to hold this sever's
				    //  address
	char buff[MAXLINE];
	time_t ticks; //required to calculate date and time

	if(argc != 2)
		err_quit("usage:<Program Name> <Port No.>");
	listenfd = Socket(AF_INET, SOCK_STREAM, 0);//create socket
	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family	=AF_INET;
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);
	servaddr.sin_port	=htons(atoi(argv[1]));
	
	Bind(listenfd, (SA *) &servaddr, sizeof(servaddr));//connect the socket
						// to an external interface;
	Listen(listenfd,LISTENQ);
	for( ; ; )
	{
		connfd = Accept(listenfd,(SA *) NULL, NULL);
		printf("Accept it or not? n/y\n");
		scanf("%c",&temp);
		getchar();
		if(temp=='y')
		{	
			ticks = time(NULL);
			printf("one client connecting:\n");
			printf("accept\n");
			snprintf(buff, sizeof(buff),"%.24s\r\n",ctime(&ticks));
			Write(connfd, buff ,strlen(buff));
		}
		else
		{
			printf("declined");
			snprintf(buff,sizeof(buff),"Declined");
			Write(connfd,buff,strlen(buff));
		}
		Close(connfd);
	}
}
