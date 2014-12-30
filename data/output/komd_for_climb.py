# -*- coding: utf-8 -*-
"""
Created on Mon Nov 10 16:01:59 2014

@author: Michele Donini

How it works:

python komd_for_climb file1;file2;...;fileN ITERATIONS %TRAINING_SET VERBOSE_MODE

where:
    file1;...;fileN are weka files.
    ITERATIONS is the number of averaged iterations.
    %TRAINING_SET is the % of examples used for the training part.
    VERBOSE actives the verbose mode if it's equal to yes or 1

e.g. the command

python komd_for_climb data1.arff;data2.arff;data2_bis.arff 10 80 yes

starts the experiments for each one of the three files,
averaging the statistics using 10 random choices of 80% training examples
and 20% of test examples. The verbose mode is ON.

"""


import sys
from Kernel import Kernel
from Import_Data import Import_Data
from MKL import MKL
import numpy as np



FILE_LIST = sys.argv[1].split(';')
ITERATIONS = int(sys.argv[2])
PERCENTUAL_TRAINING_SET = int(sys.argv[3])
VERBOSE = sys.argv[4]
if not(VERBOSE.lower() in ['1','yes']):
    VERBOSE = False
else:
    VERBOSE = True

RBF_PARAMETER_LIST = [1.0/(10**t) for t in range(10)]
LAM_PARAMETER_LIST = [x/10.0 for x in range(10)]

print 'START with'
print 'ITERATIONS:',ITERATIONS
print '% Training set:',PERCENTUAL_TRAINING_SET
print 'RBF paramaters tested:',RBF_PARAMETER_LIST
print 'Lambda KOMD tested:',LAM_PARAMETER_LIST
for f in FILE_LIST:
    print '\n\n\nFILE: ',f
    sys.stdout.flush()
    ex = Import_Data(verb=VERBOSE,max_ex=100000)
    ex.weka_stair(f,binary='False')  
    ex.resizing_data()
    Yorig = ex.Y[:]    
    
    PERCENTUAL_TRAIN = PERCENTUAL_TRAINING_SET # % of examples in the training set
    
    for r in RBF_PARAMETER_LIST:
        for l in LAM_PARAMETER_LIST:
            k = Kernel(ex)
            RBF_PARAMETER = r # sigma parameter of the RBF
            #print 'RBF Kernel evaluation with parameter sigma %f ...'%RBF_PARAMETER,
            k.RBF(RBF_PARAMETER)
            #print 'Done!'
            ClassUPauc_list = []
            ClassUPacc_list = []
            ClassUPrecallp_list = []
            ClassUPprecisionp_list = []
            auc_list = []
            acc_list = []
            recallp_list = []
            precisionp_list = []
            for it in range(ITERATIONS):
                ex.Y = Yorig[:]
                idx_train, idx_test = ex.percentual_idx(PERCENTUAL_TRAIN)
                k.idx_train = idx_train
                k.idx_test = idx_test
                LAM_PARAMETER = l # lambda parameters of KOMD
                for i in range(ex.Y.size[1]):
                    if Yorig[i] == 2:
                        ex.Y[i] = +1
                    else:
                        ex.Y[i] = -1
                if VERBOSE:
                    print 'Examples distribution'
                    print 'Train STAIR,NOT_STAIR:',len([i for i in range(len(ex.Y)) if ex.Y[i]==1 and i in idx_train]),',',len([i for i in range(len(ex.Y)) if ex.Y[i]==-1 and i in idx_train])
                    print 'Test STAIR,NOT_STAIR:,',len([i for i in range(len(ex.Y)) if ex.Y[i]==1 and i in idx_test]),',',len([i for i in range(len(ex.Y)) if ex.Y[i]==-1 and i in idx_test])
                m = MKL(ex,k)
                gamma = m.solve_omd(k,LAM_PARAMETER)
                #print 'Done!'
                auc_list.append(m.AUC_omd(k,gamma))
                acc_list.append(m.ACCURACY_omd(k,gamma))
                recallp_list.append(m.RECALL_omd(k,gamma))
                precisionp_list.append(m.PRECISION_omd(k,gamma))
                if VERBOSE or it==ITERATIONS-1:
                    print 'SIGMA_RBF: ',RBF_PARAMETER,' LAMBDA_KOMD: ',LAM_PARAMETER
                if VERBOSE:
                    print 'AUC: ',auc_list[-1]
                    print 'ACC: ',acc_list[-1]
                    print 'RECALL: ',recallp_list[-1]
                    print 'PRECISION: ',precisionp_list[-1]
                if VERBOSE or it==ITERATIONS-1:
                    print 'AUC average @',(it+1),': ',np.mean(auc_list),'+-',np.std(auc_list)
                    print 'ACC average @',(it+1),': ',np.mean(acc_list),'+-',np.std(acc_list)
                    print 'RECALL average @',(it+1),': ',np.mean(recallp_list),'+-',np.std(recallp_list)
                    print 'PRECISION average @',(it+1),': ',np.mean(precisionp_list),'+-',np.std(precisionp_list)
                    sys.stdout.flush()
            print '\n\n'
                
