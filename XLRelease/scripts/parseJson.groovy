// Exported from:        http://slsp03-adopt-devchef.iad.collab.net:5516/#/templates/Folder3ddd92a74c224609ac22e8c3e11b69e5-Releasea3d9f06d27294723a8ffc7b472fea48d/releasefile
// Release version:      9.8.0
// Date created:         Tue Mar 09 10:47:04 EST 2021

xlr {
  template('parseJson') {
    folder('scripts')
    variables {
      stringVariable('jsonString') {
        required false
        showOnReleaseStart false
        multiline true
      }
      stringVariable('projectName') {
        required false
        showOnReleaseStart false
      }
      stringVariable('buildDefinitionName') {
        required false
        showOnReleaseStart false
      }
      stringVariable('releaseDefinitionName') {
        required false
        showOnReleaseStart false
      }
      mapVariable('azBoards') {
        required false
        showOnReleaseStart false
      }
    }
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2021-03-03T09:00:00-0500')
    dueDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2021-03-05T03:44:42-0500')
    phases {
      phase('New Phase') {
        tasks {
          custom('CreateBranch') {
            owner 'admin'
            script {
              type 'github.CreateBranch'
              server 'MyGitHub'
              repositoryName 'NewTestRepo'
              oldBranch 'main'
              newBranch 'feature'
            }
          }
          script('FileInputJson') {
            script (['''\
import json

with open('/tmp/xlr-inputs.json', 'r') as file:
    data = file.read()
    
data = json.loads(data)
releaseVariables['jsonString'] = data

for rec in data:
    print("Project Name : %s" % rec['projectName'])
    releaseVariables['projectName'] = rec['projectName']
    releaseVariables['buildDefinitionName'] = rec['buildDefinitionName']
    releaseVariables['releaseDefinitionName'] = rec['releaseDefinitionName']
    releaseVariables['azBoards']['taskNumber'] = rec['azBoards']['taskNumber']
    releaseVariables['azBoards']['pbiNumber'] = rec['azBoards']['pbiNumber']
'''])
          }
          custom('print variables') {
            script {
              type 'seleniumgrid.ExecuteTests'
              
            }
          }
          gate('gate') {
            
          }
          custom('ree') {
            script {
              type 'vault.SecretsV1-ReadDynamicSecret'
              vaultServer 'MyHashiVault'
            }
          }
        }
      }
    }
    
  }
}