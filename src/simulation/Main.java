package simulation;
import java.io.IOException;
import java.util.*;

import com.sun.scenario.Settings;

public class Main {

	public static void main(String[] args) {

		//Getting settings from the file
		SimulationSettings settings ;
		try {
			settings = new SimulationSettings("./util/settings");
		} catch (Exception e1) {
			System.out.println("The system did not find the settings file.\n Using default settings");
			settings = new SimulationSettings();
		}
		List<User> userList = new ArrayList<>();
		
		int nbTradtionalCores,nbAcceleratedCores,nbSpecializedCores;
		int nbTradtionalCoresS,nbAcceleratedCoresS,nbSpecializedCoresS;
		int nbTradtionalCoresM,nbAcceleratedCoresM,nbSpecializedCoresM;
		int nbTradtionalCoresL,nbAcceleratedCoresL,nbSpecializedCoresL;
		int nbResearcher,nbComputerUser,nbWaterUser,nbSoilUser;

		Date startingDate,endingDate,currentDate;
		
		Job newJob;
		int duration;
		int nbRequestedCore;
		
		
		startingDate= new Date("01/04/2017");
		currentDate = startingDate;
		endingDate = new Date(startingDate.getTime()+1000L*60*60*24*7*settings.get("ENTIRE_TIME").intValue());
		
		RandomGenerator rd = new RandomGenerator(settings);

		//Queue initialization
		nbTradtionalCores = settings.get("NODES_PROCESSOR_NUMBER").intValue();
		nbAcceleratedCores = settings.get("ACCELERATED_PROCESSOR_NUMBER").intValue();
		nbSpecializedCores = settings.get("SPECIALIZED_NUMBER").intValue();

		nbTradtionalCoresS = (int) (0.2*nbTradtionalCores);
		nbTradtionalCoresM = (int) (0.3*nbTradtionalCores);
		nbTradtionalCoresL = nbTradtionalCores-nbTradtionalCoresS-nbTradtionalCoresM;

		nbAcceleratedCoresS = (int) (0.2*nbAcceleratedCores);
		nbAcceleratedCoresM = (int) (0.3*nbAcceleratedCores);
		nbAcceleratedCoresL = nbAcceleratedCores-nbAcceleratedCoresS-nbAcceleratedCoresM;

		nbSpecializedCoresS = (int) (0.2*nbSpecializedCores);
		nbSpecializedCoresM = (int) (0.3*nbSpecializedCores);
		nbSpecializedCoresL = nbSpecializedCores-nbSpecializedCoresS-nbSpecializedCoresM;


		SmallQueue smallQ = new SmallQueue(nbTradtionalCoresS,nbAcceleratedCoresS,nbSpecializedCoresS,settings);
		MediumQueue mediumQ = new MediumQueue(nbTradtionalCoresM,nbAcceleratedCoresM,nbSpecializedCoresM,settings);
		LargeQueue largeQ = new LargeQueue(nbTradtionalCoresL,nbAcceleratedCoresL,nbSpecializedCoresL,settings);
		HugeQueue hugeQ = new HugeQueue(nbTradtionalCores,nbAcceleratedCores,nbSpecializedCores,settings);

		//User initialization
		nbResearcher = settings.get("RESEARCHER_NUMBER").intValue();
		nbComputerUser = (int) (settings.get("STUDENT_NUMBER")/3);
		nbWaterUser = (int) (settings.get("STUDENT_NUMBER")/3);
		nbSoilUser = settings.get("STUDENT_NUMBER").intValue() - nbComputerUser - nbWaterUser;

		for(int i = 0 ; i<= nbComputerUser ;i++) {
			userList.add(new User(UserType.computer,settings.get("COMPUTER_BUDGET")));
		}
		for(int i = 0 ; i<= nbWaterUser ;i++) {
			userList.add(new User(UserType.water,settings.get("WATER_BUDGET")));
		}
		for(int i = 0 ; i<= nbSoilUser ;i++) {
			userList.add(new User(UserType.soil,settings.get("SOIL_BUDGET")));
		}
		for(int i = 0 ; i<= nbResearcher ;i++) {
			userList.add(new User(UserType.researcher,settings.get("RESEARCHER_BUDGET")));
		}

		//Jobs simulation
		while(currentDate.before(endingDate)) {//generate jobs
			currentDate = new Date(currentDate.getTime()+rd.getTimeBetweenJobs()*1000);//Moving forward in time
			nbRequestedCore = rd.getJobSize();
			duration = rd.getJobTime();
			
			if(nbRequestedCore <= 2 && duration <= 3600 ) {//small jobs
				newJob = new Job(nbRequestedCore, currentDate, duration,smallQ );
				if(! smallQ.addJob(newJob)) System.out.println("false small");
				
			}else if (nbRequestedCore <= settings.getTotalNumberNodes()*0.1 && duration <= 3600*8) {//Medium jobs
				newJob = new Job(nbRequestedCore, currentDate, duration, mediumQ);
				if(! mediumQ.addJob(newJob)) System.out.println("false mediumQ");
				
			}else if(nbRequestedCore <= settings.getTotalNumberNodes()*0.5 && duration <= 3600*16) {//Large jobs
				newJob = new Job(nbRequestedCore, currentDate, duration, largeQ);
				if(! largeQ.addJob(newJob)) System.out.println("false largeQ");
				
			}else {//huge jobs
				newJob = new Job(nbRequestedCore, currentDate, duration, hugeQ);
				if(! hugeQ.addJob(newJob)) System.out.println("false hugeQ"+newJob.getDuration());
				
			}
		}
		System.out.println("there are "+smallQ.getNbJobInTheQueue()+" small jobs");
		System.out.println("there are "+mediumQ.getNbJobInTheQueue()+" medium jobs");
		System.out.println("there are "+largeQ.getNbJobInTheQueue()+" large jobs");
		System.out.println("there are "+hugeQ.getNbJobInTheQueue()+" huge jobs");
		mediumQ.processJobs();

	}

}
